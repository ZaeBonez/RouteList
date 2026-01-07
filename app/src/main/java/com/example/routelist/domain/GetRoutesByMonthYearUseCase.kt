package com.example.routelist.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRoutesByMonthYearUseCase @Inject constructor(
    private val repository: RouteRepository
) {
    operator fun invoke(year: String, month: String): Flow<List<RouteListInfo>> {
        return repository.getRoutesByMonthYear(year, month)
    }
}