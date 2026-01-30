package com.example.routelist.presentation.mainActivity

import androidx.lifecycle.viewModelScope
import com.example.routelist.domain.GetRoutesByMonthYearUseCase
import com.example.routelist.domain.RouteListInfo
import com.example.routelist.presentation.mainActivity.base.BaseViewModel
import com.example.routelist.presentation.mainActivity.model.RouteListItem
import com.example.routelist.presentation.mainActivity.router.MainRouter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

class RouteViewModel @Inject constructor(
    private val getRoutesByMonthYearUseCase: GetRoutesByMonthYearUseCase,
    override val router: MainRouter,
    private val routeListFactory: RouteListFactory
) : BaseViewModel<List<RouteListItem>, Nothing>(emptyList()) {

    private val selectedPeriod = MutableStateFlow(getCurrentPeriod())
    private val selectedHeader = MutableStateFlow(getCurrentHeader())

    init {
        loadRoutes()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadRoutes() {
        viewModelScope.launch {
            selectedPeriod.flatMapLatest { (year, month) ->
                getRoutesByMonthYearUseCase(year, month)
            }.collect { routeList ->
                val (month0, yearInt) = selectedHeader.value
                setState { buildUiList(routeList, month0, yearInt) }
            }
        }
    }

    private fun getCurrentPeriod(): Pair<String, String> {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR).toString()
        val month = (cal.get(Calendar.MONTH) + 1).toString().padStart(2, '0')
        return year to month
    }

    private fun getCurrentHeader(): Pair<Int, Int> {
        val cal = Calendar.getInstance()
        val month0 = cal.get(Calendar.MONTH)
        val year = cal.get(Calendar.YEAR)
        return month0 to year
    }

    fun setMonthYear(monthZeroBased: Int, year: Int) {

        selectedHeader.value = monthZeroBased to year

        val yearStr = year.toString()
        val monthStr = (monthZeroBased + 1).toString().padStart(2, '0')
        selectedPeriod.value = yearStr to monthStr
    }

    fun openRouteDetails(routeItem: RouteListItem.RouteItem) {
        router.openRouteDetails(routeItem)
    }

    fun openAddRoute() {
        router.openAddRoute()
    }

    fun back() {
        router.routeBack()
    }

    private fun buildUiList(
        routeList: List<RouteListInfo>, headerMonthZeroBased: Int, headerYear: Int
    ): List<RouteListItem> {
        return routeListFactory.buildUiList(routeList, headerMonthZeroBased, headerYear)
    }

}