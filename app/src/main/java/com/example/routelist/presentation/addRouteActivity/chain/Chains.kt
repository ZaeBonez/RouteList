package com.example.routelist.presentation.addRouteActivity.chain

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NumberChain(override val newChain: AddRouteChain?) : AddRouteChain.Base() {

    override fun isInvalid(model: AddRouteChainModel): Boolean {
        return model.number.isNullOrBlank()
    }

    override fun getErrorText(): String {
        return "Введите номер маршрута"
    }
}

class TrainNumberChain(override val newChain: AddRouteChain?) : AddRouteChain.Base() {

    override fun isInvalid(model: AddRouteChainModel): Boolean {
        return model.trainNumber.isNullOrBlank()
    }

    override fun getErrorText(): String {
        return "Введите номер поезда"
    }
}

class CarriageCountChain(override val newChain: AddRouteChain?) : AddRouteChain.Base() {

    override fun isInvalid(model: AddRouteChainModel): Boolean {
        return model.carriageCount.isNullOrBlank()
    }

    override fun getErrorText(): String {
        return "Введите количество вагонов"
    }
}

class StartStationChain(override val newChain: AddRouteChain?) : AddRouteChain.Base() {

    override fun isInvalid(model: AddRouteChainModel): Boolean {
        return model.startStation.isNullOrBlank()
    }

    override fun getErrorText(): String {
        return "Введите станцию отправления"
    }
}

class EndStationChain : AddRouteChain.Base() {

    override fun isInvalid(model: AddRouteChainModel): Boolean {
        return model.endStation.isNullOrBlank()
    }

    override fun getErrorText(): String {
        return "Введите станцию назначения"
    }
}

abstract class DateChain : AddRouteChain.Base() {

    protected abstract fun getDate(model: AddRouteChainModel): String?

    override fun isInvalid(model: AddRouteChainModel): Boolean {
        val date = getDate(model)
        return date.isNullOrBlank() || parseDate(date) == null
    }

    override fun getErrorText(): String = "Введите номер маршрута"

    private fun parseDate(s: String) = try {
        LocalDateTime.parse(s, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
    } catch (_: Exception) {
        null
    }
}

class StartDateChain(override val newChain: AddRouteChain?) : DateChain() {

    override fun getDate(model: AddRouteChainModel): String? = model.startDate
}

class EndDateChain(override val newChain: AddRouteChain?) : DateChain() {
    override fun getDate(model: AddRouteChainModel): String? = model.endDate
}