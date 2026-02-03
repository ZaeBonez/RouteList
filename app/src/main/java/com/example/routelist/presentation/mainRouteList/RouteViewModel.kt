package com.example.routelist.presentation.mainRouteList

import androidx.lifecycle.viewModelScope
import com.example.routelist.domain.GetRoutesByMonthYearUseCase
import com.example.routelist.domain.RouteListInfo
import com.example.routelist.presentation.base.BaseViewModel
import com.example.routelist.presentation.mainRouteList.model.RouteListItem
import com.example.routelist.presentation.mainRouteList.router.MainRouter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class RouteViewModel @Inject constructor(
    private val getRoutesByMonthYearUseCase: GetRoutesByMonthYearUseCase,
    override val router: MainRouter,
    private val routeListFactory: RouteListFactory,
    private val dateProvider: DateProvider
) : BaseViewModel<RouteListState, Any>(
    RouteListState(
        selectedPeriod = dateProvider.getCurrentPeriod(),
        selectedHeader = dateProvider.getCurrentHeader(),
        items = emptyList()
    )
) {
    init {
        loadRoutes()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadRoutes() {
        viewModelScope.launch {
            getStateFlow()
                .map { it.selectedPeriod }
                .distinctUntilChanged()
                .flatMapLatest { (year, month) ->
                    getRoutesByMonthYearUseCase(year, month)
                }.collect { routeList ->
                    val (month0, yearInt) = state.value.selectedHeader
                    setState {
                        copy(
                            items = buildUiList(routeList, month0, yearInt)
                        )
                    }
                }
        }
    }

    fun setMonthYear(monthZeroBased: Int, year: Int) {
        val yearStr = year.toString()
        val monthStr = (monthZeroBased + 1).toString().padStart(2, '0')

        setState {
            copy(
                selectedHeader = monthZeroBased to year,
                selectedPeriod = yearStr to monthStr
            )
        }
    }

    fun openRouteDetails(routeItem: RouteListItem.RouteItem) {
        router.openRouteDetails(routeItem)
    }

    fun openAddRoute() {
        router.openAddRoute()
    }

    private fun buildUiList(
        routeList: List<RouteListInfo>, headerMonth: Int, headerYear: Int
    ): List<RouteListItem> {
        return routeListFactory.buildUiList(routeList, headerMonth, headerYear)
    }

}