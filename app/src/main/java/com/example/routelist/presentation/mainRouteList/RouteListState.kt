package com.example.routelist.presentation.mainRouteList

import com.example.routelist.presentation.mainRouteList.model.RouteListItem

data class RouteListState (
    val selectedPeriod: Pair<String, String>,
    val selectedHeader: Pair<Int, Int>,
    val items: List<RouteListItem> = emptyList()
)