package com.example.routelist.data.repository

import com.example.routelist.data.database.RouteInfoDao
import com.example.routelist.data.mapper.RouteMapper
import com.example.routelist.domain.RouteListInfo
import com.example.routelist.domain.RouteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RouteRepositoryImpl @Inject constructor(
    private val mapper: RouteMapper,
    private val routeInfoDao: RouteInfoDao,
) : RouteRepository {

    override fun getRouteInfoList(): Flow<List<RouteListInfo>> =
        routeInfoDao.getAllRoutesFlow().map { list ->
            list.map { mapper.mapDbToInfo(it) }
        }

    override fun getRouteInfo(id: Int): List<RouteListInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun insertRoute(route: RouteListInfo) {
        routeInfoDao.insertRoute(mapper.mapInfoToDb(route))
    }

    override suspend fun deleteRouteById(id: Int) {
        routeInfoDao.deleteRouteById(id)
    }

    override fun getRoutesByMonthYear(
        year: String,
        month: String
    ): Flow<List<RouteListInfo>> =
        routeInfoDao.getRoutesByMonthYearFlow(
            year = year.trim(),
            month = month.trim().padStart(2, '0')
        ).map { list ->
            list.map { mapper.mapDbToInfo(it) }
        }

}