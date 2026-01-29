package com.example.routelist.presentation.addRouteActivity


import androidx.lifecycle.viewModelScope
import com.example.routelist.R
import com.example.routelist.domain.InsertRouteUseCase
import com.example.routelist.domain.RouteListInfo
import com.example.routelist.presentation.addRouteActivity.chain.AddRouteChain
import com.example.routelist.presentation.addRouteActivity.chain.AddRouteChainModel
import com.example.routelist.presentation.addRouteActivity.model.AddRouteEffect
import com.example.routelist.presentation.addRouteActivity.model.AddRouteState
import com.example.routelist.presentation.addRouteActivity.model.RouteNumber
import com.example.routelist.presentation.mainActivity.base.BaseViewModel
import com.example.routelist.presentation.mainActivity.router.MainRouter
import kotlinx.coroutines.launch
import javax.inject.Inject


class AddRouteViewModel @Inject constructor(
    private val insertRouteUseCase: InsertRouteUseCase,
    private val addRouteChain: AddRouteChain,
    override val router: MainRouter
) : BaseViewModel<AddRouteState, AddRouteEffect>(AddRouteState()) {

    fun updateRouteNumber(number: String) {
        setState { copy(routeNumber = RouteNumber(number)) }
    }

    fun updateStartDateRow(start: String) {
        val current = state.value
        setState {
            copy(dateRow = current.dateRow.copy(startDate = start))
        }
    }

    fun updateEndDateRow(end: String) {
        val current = state.value
        setState {
            copy(dateRow = current.dateRow.copy(endDate = end))
        }
    }


    fun updateTrainNumber(trainNum: String) {
        val current = state.value
        setState {
            copy(trainInfo = current.trainInfo.copy(trainNumber = trainNum))
        }
    }

    fun updateCarriageCount(carriageCount: String) {
        val current = state.value
        setState {
            copy(trainInfo = current.trainInfo.copy(carriageCount = carriageCount))
        }
    }

    fun updateStartStation(startStation: String) {
        val current = state.value
        setState {
            copy(trainInfo = current.trainInfo.copy(startStation = startStation))
        }

    }

    fun updateEndStation(endStation: String) {
        val current = state.value
        setState {
            copy(trainInfo = current.trainInfo.copy(endStation = endStation))
        }
    }

    fun updateDistance(distance: String) {
        val current = state.value
        setState {
            copy(trainInfo = current.trainInfo.copy(distance = distance))
        }
    }

    fun updateCountStop(stopsCount: String) {
        val current = state.value
        setState {
            copy(trainInfo = current.trainInfo.copy(stopsCount = stopsCount))
        }
    }


    fun updatePassengerNumber(number: String) {
        val current = state.value
        state.value = current.copy(
            passengerInfo = current.passengerInfo.copy(passengerTrainNumber = number)
        )
    }

    fun updatePassengerStartDateRow(start: String) {
        val current = state.value
        setState {
            copy(
                passengerInfo = current.passengerInfo.copy(passengerStartDate = start)
            )
        }
    }

    fun updatePassengerEndDateRow(end: String) {
        val current = state.value
        setState {
            copy(
                passengerInfo = current.passengerInfo.copy(passengerEndDate = end)
            )
        }
    }

    fun routerBack() = router.routeBack()

    fun saveRoute() {
        viewModelScope.launch {
            val save = state.value

            val entity = RouteListInfo(
                routeNumber = save.routeNumber.number.toString(),
                startDate = save.dateRow.startDate.toString(),
                endDate = save.dateRow.endDate.toString(),
                trainNumber = save.trainInfo.trainNumber.toString(),
                composition = save.trainInfo.carriageCount.toString(),
                startStation = save.trainInfo.startStation.toString(),
                endStation = save.trainInfo.endStation.toString(),
                distance = save.trainInfo.distance.toString(),
                stopsCount = save.trainInfo.stopsCount.toString(),
                passengerTrainNumber = save.passengerInfo.passengerTrainNumber,
                passengerStartDate = save.passengerInfo.passengerStartDate,
                passengerEndDate = save.passengerInfo.passengerEndDate
            )

            insertRouteUseCase(entity)
        }
    }

    fun saveRouteV2() {
        val error = state.value.run {
            addRouteChain.validate(
                AddRouteChainModel(
                    number = routeNumber.number,
                    startDate = dateRow.startDate,
                    endDate = dateRow.endDate,
                    trainNumber = trainInfo.trainNumber,
                    carriageCount = trainInfo.carriageCount,
                    startStation = trainInfo.startStation,
                    endStation = trainInfo.endStation,
                )
            )
        }

        viewModelScope.launch {
            effects.emit(AddRouteEffect.ShowToast(error ?: R.string.route_saved))
            if (error == null) {
                saveRoute()
                effects.emit(AddRouteEffect.NavigateBackStack)
            }
        }
    }
}