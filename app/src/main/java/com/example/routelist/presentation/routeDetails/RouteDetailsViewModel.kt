package com.example.routelist.presentation.routeDetails

import androidx.lifecycle.viewModelScope
import com.example.routelist.domain.DeleteRouteUseCase
import com.example.routelist.presentation.mainActivity.base.BaseViewModel
import com.example.routelist.presentation.mainActivity.router.MainRouter
import com.example.routelist.presentation.routeDetails.model.AmountDetails
import com.example.routelist.presentation.routeDetails.model.RouteArgs
import com.example.routelist.presentation.routeDetails.model.RouteDetailsState
import com.example.routelist.presentation.routeDetails.utils.NightHoursCalculator
import kotlinx.coroutines.launch
import javax.inject.Inject

class RouteDetailsViewModel @Inject constructor(
    private val deleteRouteUseCase: DeleteRouteUseCase,
    private val calculator: NightHoursCalculator,
    override val router: MainRouter,
) : BaseViewModel<RouteDetailsState, Any>(RouteDetailsState()) {

    fun init(args: RouteArgs) {

        val nightMins = calculator.calculateNightMinutes(args.start, args.end)
        val nightText = calculator.formatNightMinutes(nightMins)

        setState {
            copy(
                routeArgs = args,
                nightTime = nightText,
                amountsDetails = AmountDetails()
            )
        }
    }

    fun routeBack() = router.routeBack()
    fun deleteRoute(id: Int) {
        viewModelScope.launch {
            deleteRouteUseCase.deleteRouteById(id)
        }
    }
}

