package com.example.routelist.presentation.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.routelist.domain.GetRoutesByMonthYearUseCase
import com.example.routelist.domain.RouteListInfo
import com.example.routelist.presentation.mainActivity.model.RouteListItem
import com.example.routelist.presentation.mainActivity.model.RoutePosition
import com.example.routelist.presentation.mainActivity.router.MainRouter
import com.example.routelist.presentation.mainActivity.utils.RouteTimeUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

class RouteViewModel @Inject constructor(
    private val getRoutesByMonthYearUseCase: GetRoutesByMonthYearUseCase,
    override val router: MainRouter = MainRouter(),
    private val timeCalculator: RouteTimeUtils
) : BaseViewModel<List<RouteListItem>>(emptyList()) {

    private val _items = MutableLiveData<List<RouteListItem>>()
    val items: LiveData<List<RouteListItem>> get() = _items

    private val selectedPeriod = MutableStateFlow(getCurrentPeriod())
    private val selectedHeader = MutableStateFlow(getCurrentHeader())

    init {
        loadRoutes()
    }

    private fun loadRoutes() {
        viewModelScope.launch {
            selectedPeriod.flatMapLatest { (year, month) ->
                getRoutesByMonthYearUseCase(year, month)
            }.collect { routeList ->
                val (month0, yearInt) = selectedHeader.value
                _items.postValue(buildUiList(routeList, month0, yearInt))
            }
        }
    }

    private fun getCurrentPeriod(): Pair<String, String> {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR).toString()
        val month = (cal.get(Calendar.MONTH) + 1).toString().padStart(2, '0')
        return year to month
    }

    private fun getCurrentHeader(): Pair<Int, Int> {
        val cal = Calendar.getInstance()
        val month0 = cal.get(Calendar.MONTH)
        val year = cal.get(Calendar.YEAR)
        return month0 to year
    }

    fun setMonthYear(monthZeroBased: Int, year: Int) {

        selectedHeader.value = monthZeroBased to year

        val yearStr = year.toString()
        val monthStr = (monthZeroBased + 1).toString().padStart(2, '0')
        selectedPeriod.value = yearStr to monthStr
    }

    fun openRouteDetails(routeItem: RouteListItem.RouteItem) {
        router.openRouteDetails(routeItem)
    }

    private fun buildUiList(
        routeList: List<RouteListInfo>, headerMonthZeroBased: Int, headerYear: Int
    ): List<RouteListItem> {
        // Отдельный класс Factory - DefaultRouteListFactory - buildUiList
        val totalHours = routeList.sumOf { timeCalculator.workedHours(it) }
        val nightHours = routeList.sumOf { timeCalculator.nightHours(it) }
        val passengerHours = 0

        val ui = mutableListOf<RouteListItem>()

        ui.add(RouteListItem.CalendarHeader(headerMonthZeroBased, headerYear))

        ui.add(RouteListItem.Card("Норма часов", "160"))
        ui.add(RouteListItem.Card("Норма на сегодня", "60"))
        ui.add(RouteListItem.Card("Всего", timeCalculator.toHoursString(totalHours)))
        ui.add(RouteListItem.Card("Пассажиром", passengerHours.toString()))
        ui.add(RouteListItem.Card("Ночных", timeCalculator.toHoursString(nightHours)))

        ui.add(RouteListItem.RoutesHeader)

        if (routeList.isEmpty()) return ui

        ui.add(RouteListItem.RoutesTableHeaders)

        routeList.forEachIndexed { index, route ->
            val pos = when {
                routeList.size == 1 -> RoutePosition.Last
                index == routeList.lastIndex -> RoutePosition.Last
                else -> RoutePosition.Middle
            }
            ui.add(
                RouteListItem.RouteItem(
                    id = route.id,
                    trainNumber = route.routeNumber,
                    start = route.startDate,
                    end = route.endDate,
                    hours = timeCalculator.toHoursString(timeCalculator.workedHours(route)),
                    routePosition = pos
                )
            )
        }

        return ui
    }

}