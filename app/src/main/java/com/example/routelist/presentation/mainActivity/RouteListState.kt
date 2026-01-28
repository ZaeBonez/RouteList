package com.example.routelist.presentation.mainActivity

import com.example.routelist.presentation.mainActivity.model.RouteListItem

data class RouteListState(
    val items: List<RouteListItem> = emptyList(),
    val header: Pair<Int, Int> = Pair(0, 0),
    val period: Pair<String, String> = Pair("", ""),
)
