package com.example.routelist.presentation.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.routelist.domain.GetRouteInfoListUseCase
import com.example.routelist.domain.RouteListInfo
import com.example.routelist.presentation.mainActivity.model.RouteListItem
import com.example.routelist.presentation.mainActivity.model.RoutePosition
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import javax.inject.Inject

class RouteViewModel @Inject constructor(
    private val getRouteInfoListUseCase: GetRouteInfoListUseCase
) : ViewModel() {

    private val _items = MutableLiveData<List<RouteListItem>>()
    val items: LiveData<List<RouteListItem>> get() = _items

    init {
        loadRoutes()
    }

    private fun loadRoutes() {
        viewModelScope.launch {
            getRouteInfoListUseCase().collect { routeList ->
                val uiList = buildUiList(routeList)
                _items.postValue(uiList)
            }
        }
    }

    private fun buildUiList(routeList: List<RouteListInfo>): List<RouteListItem> {
        val totalHours = routeList.sumOf { it.getWorkedHours() }
        val nightHours = routeList.sumOf { it.getNightHours() }
        val passengerHours = 0

        val ui = mutableListOf<RouteListItem>()

        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        ui.add(RouteListItem.CalendarHeader(month, year))

        // Карточки
        ui.add(RouteListItem.Card("Норма часов", "160"))
        ui.add(RouteListItem.Card("Норма на сегодня", "60"))
        ui.add(RouteListItem.Card("Всего", totalHours.toHoursString()))
        ui.add(RouteListItem.Card("Пассажиром", passengerHours.toString()))
        ui.add(RouteListItem.Card("Ночных", nightHours.toHoursString()))

        // Заголовок списка
        ui.add(RouteListItem.RoutesHeader)

        // Заголовок Таблицы
        ui.add(RouteListItem.RoutesTableHeaders)

        // Маршруты
        routeList.forEachIndexed { index, route ->
            val pos = when (index) {
                0 -> RoutePosition.First
                routeList.lastIndex -> RoutePosition.Last
                else -> RoutePosition.Middle
            }
            ui.add(
                RouteListItem.RouteItem(
                    trainNumber = route.routeNumber,
                    start = route.startDate,
                    end = route.endDate,
                    hours = route.getWorkedHours().toHoursString(),
                    routePosition = pos
                )
            )
        }

        return ui
    }


    private fun RouteListInfo.getWorkedHours(): Long {
        return calculateHours(startDate, endDate)
    }


    private fun RouteListInfo.getNightHours(): Long {
        return calculateNightHours(startDate, endDate)
    }

    private fun Long.toHoursString(): String {
        val hours = this / 60
        val minutes = this % 60
        return "%d:%02d".format(hours, minutes)
    }


    private fun calculateHours(start: String, end: String): Long {

        val s = safeParse(start) ?: return 0
        val e = safeParse(end) ?: return 0

        return Duration.between(s, e).toMinutes()
    }

    private fun calculateNightHours(start: String, end: String): Long {

        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")

        val s = safeParse(start) ?: return 0
        val e = safeParse(end) ?: return 0

        var current = s
        val endTime = e
        var totalMinutes = 0L

        while (current.isBefore(endTime)) {
            val hour = current.hour
            if (hour >= 22 || hour < 6) {
                totalMinutes++
            }
            current = current.plusMinutes(1)
        }

        return totalMinutes
    }


    private fun safeParse(date: String?): LocalDateTime? {
        if (date.isNullOrBlank()) return null
        return try {
            LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
        } catch (e: Exception) {
            null
        }
    }
}