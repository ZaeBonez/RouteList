package com.example.routelist.presentation.mainActivity

import com.example.routelist.domain.RouteListInfo
import com.example.routelist.presentation.mainActivity.model.RouteListItem

interface RouteListFactory {
    fun buildUiList(
        routeList: List<RouteListInfo>,
        headerMonth: Int,
        headerYear: Int
    ): List<RouteListItem>
}