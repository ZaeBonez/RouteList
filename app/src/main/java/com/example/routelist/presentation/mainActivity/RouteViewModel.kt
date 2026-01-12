package com.example.routelist.presentation.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.routelist.domain.GetRouteInfoListUseCase
import com.example.routelist.domain.GetRoutesByMonthYearUseCase
import com.example.routelist.domain.RouteListInfo
import com.example.routelist.presentation.mainActivity.model.RouteListItem
import com.example.routelist.presentation.mainActivity.model.RoutePosition
import com.example.routelist.presentation.mainActivity.utils.RouteTimeUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

class RouteViewModel @Inject constructor(
    private val getRouteInfoListUseCase: GetRouteInfoListUseCase,
    private val getRoutesByMonthYearUseCase: GetRoutesByMonthYearUseCase
) : ViewModel() {

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

    private fun buildUiList(
        routeList: List<RouteListInfo>, headerMonthZeroBased: Int, headerYear: Int
    ): List<RouteListItem> {
        val totalHours = routeList.sumOf { RouteTimeUtils.workedHours(it) }
        val nightHours = routeList.sumOf { RouteTimeUtils.nightHours(it) }
        val passengerHours = 0

        val ui = mutableListOf<RouteListItem>()

        ui.add(RouteListItem.CalendarHeader(headerMonthZeroBased, headerYear))

        ui.add(RouteListItem.Card("Норма часов", "160"))
        ui.add(RouteListItem.Card("Норма на сегодня", "60"))
        ui.add(RouteListItem.Card("Всего", RouteTimeUtils.toHoursString(totalHours)))
        ui.add(RouteListItem.Card("Пассажиром", passengerHours.toString()))
        ui.add(RouteListItem.Card("Ночных", RouteTimeUtils.toHoursString(nightHours)))

        ui.add(RouteListItem.RoutesHeader)

        if (routeList.isNotEmpty()) {
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
                        hours = RouteTimeUtils.toHoursString(RouteTimeUtils.workedHours(route)),
                        routePosition = pos
                    )
                )
            }
        }
        return ui
    }

}