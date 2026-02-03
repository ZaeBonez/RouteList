package com.example.routelist.presentation.mainRouteList

import com.example.routelist.domain.RouteListInfo
import com.example.routelist.presentation.mainRouteList.model.RouteListItem

interface RouteListFactory {
    fun buildUiList(
        routeList: List<RouteListInfo>,
        headerMonth: Int,
        headerYear: Int
    ): List<RouteListItem>
}