package com.example.routelist.presentation.mainActivity

import com.example.routelist.domain.RouteListInfo
import com.example.routelist.presentation.mainActivity.model.RouteListItem
import com.example.routelist.presentation.mainActivity.model.RoutePosition
import com.example.routelist.presentation.mainActivity.utils.RouteTimeUtils
import javax.inject.Inject

class DefaultRouteListFactory @Inject constructor (
    private val timeCalculator: RouteTimeUtils
) : RouteListFactory {
    override fun buildUiList(
        routeList: List<RouteListInfo>,
        headerMonth: Int,
        headerYear: Int
    ): List<RouteListItem> {

        val totalHours = routeList.sumOf { timeCalculator.workedHours(it) }
        val nightHours = routeList.sumOf { timeCalculator.nightHours(it) }
        val passengerHours = 0

        val ui = mutableListOf<RouteListItem>()

        ui.add(RouteListItem.CalendarHeader(headerMonth, headerYear))

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