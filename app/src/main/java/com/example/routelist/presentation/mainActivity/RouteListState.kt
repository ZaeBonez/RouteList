package com.example.routelist.presentation.mainActivity

import com.example.routelist.presentation.mainActivity.model.RouteListItem

data class RouteListState (
    val selectedPeriod: Pair<String, String>,
    val selectedHeader: Pair<Int, Int>,
    val items: List<RouteListItem> = emptyList()
)