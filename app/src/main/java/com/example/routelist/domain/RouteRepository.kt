package com.example.routelist.domain

import kotlinx.coroutines.flow.Flow

interface RouteRepository {

    fun getRouteInfoList(): Flow<List<RouteListInfo>>

    fun getRouteInfo(id: Int): List<RouteListInfo>

    suspend fun insertRoute(route: RouteListInfo)

    suspend fun deleteRouteById(id: Int)

    fun getRoutesByMonthYear(year: String, month: String): Flow<List<RouteListInfo>>
}