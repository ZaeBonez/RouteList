package com.example.routelist.presentation.mainRouteList.model

sealed interface RouteListItem {

    data class CalendarHeader(
        val month: Int,
        val year: Int
    ) : RouteListItem


    data object RoutesHeader : RouteListItem

    data object RoutesTableHeaders : RouteListItem

    data class Card(
        val title: String,
        val value: String
    ) : RouteListItem, SpanType.Two()

    data class RouteItem(
        val id : Int,
        val trainNumber: String,
        val start: String,
        val end: String,
        val hours: String,
        val routePosition: RoutePosition,
    ) : RouteListItem

}