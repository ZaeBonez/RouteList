package com.example.routelist.presentation.addRouteActivity.chain


import com.example.routelist.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NumberChain(override val newChain: AddRouteChain?) : AddRouteChain.Base() {

    override fun isInvalid(model: AddRouteChainModel): Boolean {
        return model.number.isNullOrBlank()
    }

    override fun getErrorText(): Int = R.string.route_number
}

class TrainNumberChain(override val newChain: AddRouteChain?) : AddRouteChain.Base() {

    override fun isInvalid(model: AddRouteChainModel): Boolean {
        return model.trainNumber.isNullOrBlank()
    }

    override fun getErrorText(): Int = R.string.train_number
}

class CarriageCountChain(override val newChain: AddRouteChain?) : AddRouteChain.Base() {

    override fun isInvalid(model: AddRouteChainModel): Boolean {
        return model.carriageCount.isNullOrBlank()
    }

    override fun getErrorText(): Int = R.string.carriage_count
}

class StartStationChain(override val newChain: AddRouteChain?) : AddRouteChain.Base() {

    override fun isInvalid(model: AddRouteChainModel): Boolean {
        return model.startStation.isNullOrBlank()
    }

    override fun getErrorText(): Int = R.string.start_station
}

class EndStationChain : AddRouteChain.Base() {

    override fun isInvalid(model: AddRouteChainModel): Boolean {
        return model.endDate.isNullOrBlank()
    }

    override fun getErrorText(): Int = R.string.end_station
}

abstract class DateChain : AddRouteChain.Base() {

    protected abstract fun getDate(model: AddRouteChainModel): String?

    override fun isInvalid(model: AddRouteChainModel): Boolean {
        val date = getDate(model)
        return date.isNullOrBlank() || parseDate(date) == null
    }

    override fun getErrorText(): Int = R.string.route_number

    private fun parseDate(s: String) = try {
        LocalDateTime.parse(s, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
    } catch (_: Exception) {
        null
    }

}

class CompareDateChain(override val newChain: AddRouteChain?) : AddRouteChain.Base() {

    override fun isInvalid(model: AddRouteChainModel): Boolean {
        val start = model.startDate ?: return false
        val end = model.endDate ?: return false
        return end < start
    }

    override fun getErrorText(): Int = R.string.inccorect_date


}

class StartDateChain(override val newChain: AddRouteChain?) : DateChain() {

    override fun getDate(model: AddRouteChainModel): String? = model.startDate

    override fun getErrorText(): Int = R.string.start_date
}

class EndDateChain(override val newChain: AddRouteChain?) : DateChain() {

    override fun getDate(model: AddRouteChainModel): String? = model.endDate

    override fun getErrorText(): Int = R.string.end_date
}