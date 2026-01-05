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
        assertEquals(R.string.route_number, result)
    }

    @Test
    fun checkStartStationError() {
        val model = validModel().copy(startStation = "")
        val result = chain.validate(model)
        assertEquals(R.string.start_station, result)
    }

    @Test
    fun checkEndStationError() {
        val model = validModel().copy(endStation = "")
        val result = chain.validate(model)
        assertEquals(R.string.end_station, result)
    }

    @Test
    fun checkTrainNumberError() {
        val model = validModel().copy(trainNumber = "")
        val result = chain.validate(model)
        assertEquals(R.string.train_number, result)
    }

    @Test
    fun checkCarriageCountError() {
        val model = validModel().copy(carriageCount = "")
        val result = chain.validate(model)
        assertEquals(R.string.carriage_count, result)
    }

    @Test
    fun checkStartDateError() {
        val model = validModel().copy(startDate = "")
        val result = chain.validate(model)
        assertEquals(R.string.start_date, result)
    }

    @Test
    fun checkEndDateError() {
        val model = validModel().copy(endDate = "")
        val result = chain.validate(model)
        assertEquals(R.string.end_date, result)
    }

//    @Test
//    fun checkKilometerCount() {
//        val model = validModel().copy()
//        val result = chain.validate(model)
//        assertEquals("Введите расстояние", result)
//    }
//
//    @Test
//    fun checkStopsCount() {
//        val model = validModel().copy()
//        val result = chain.validate(model)
//        assertEquals("Введите количество остановок", result)
//    }


    private fun validModel() = AddRouteChainModel(
        number = "1",
        trainNumber = "123",
        carriageCount = "5",
        startDate = "01.01.2026 10:00",
        endDate = "01.01.2026 12:00",
        startStation = "A",
        endStation = "B",
    )

}