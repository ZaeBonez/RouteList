package com.example.routelist

import com.example.routelist.presentation.addRouteActivity.chain.AddRouteChain
import com.example.routelist.presentation.addRouteActivity.chain.AddRouteChainModel
import com.example.routelist.presentation.addRouteActivity.chain.CarriageCountChain
import com.example.routelist.presentation.addRouteActivity.chain.EndDateChain
import com.example.routelist.presentation.addRouteActivity.chain.EndStationChain
import com.example.routelist.presentation.addRouteActivity.chain.NumberChain
import com.example.routelist.presentation.addRouteActivity.chain.StartDateChain
import com.example.routelist.presentation.addRouteActivity.chain.StartStationChain
import com.example.routelist.presentation.addRouteActivity.chain.TrainNumberChain
import org.junit.Test
import kotlin.test.assertEquals

class RouteValidatorTest {

    private val chain: AddRouteChain =
        NumberChain(
            StartDateChain(
                EndDateChain(
                    TrainNumberChain(
                        CarriageCountChain(
                            StartStationChain(
                                EndStationChain()
                            )
                        )
                    )
                )
            )
        )

    @Test
    fun checkNumberError() {
        val model = validModel().copy(number = "")
        val result = chain.validate(model)
        assertEquals("Введите номер маршрута", result)
    }

    @Test
    fun checkTrainNumberError() {
        val model = validModel().copy(trainNumber = "")
        val result = chain.validate(model)
        assertEquals("Введите номер поезда", result)
    }

    @Test
    fun checkCarriageCountError() {
        val model = validModel().copy(carriageCount = "")
        val result = chain.validate(model)
        assertEquals("Введите количество вагонов", result)
    }

    @Test
    fun checkStartDateError() {
        val model = validModel().copy(startDate = "")
        val result = chain.validate(model)
        assertEquals("Введите дату начала", result)
    }

    @Test
    fun checkEndDateError() {
        val model = validModel().copy(endDate = "")
        val result = chain.validate(model)
        assertEquals("Введите дату окончания", result)
    }

    @Test
    fun checkStartStationError() {
        val model = validModel().copy(startStation = "")
        val result = chain.validate(model)
        assertEquals("Введите станцию отправления", result)
    }

    @Test
    fun checkEndStationError() {
        val model = validModel().copy(endStation = "")
        val result = chain.validate(model)
        assertEquals("Введите станцию назначения", result)
    }

    private fun validModel() = AddRouteChainModel(
        number = "1",
        trainNumber = "123",
        carriageCount = "5",
        startDate = "2026-01-01",
        endDate = "2026-01-02",
        startStation = "A",
        endStation = "B",
    )

}