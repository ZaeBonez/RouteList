package com.example.routelist.presentation.routeDetails.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class NightHoursCalculator() {

    private val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")


    fun calculateNightMinutes(start: String, end: String): Int {
        val startDt = try { LocalDateTime.parse(start, formatter) } catch (e: Exception) { return 0 }
        val endDt = try { LocalDateTime.parse(end, formatter) } catch (e: Exception) { return 0 }

        var nightMinutes = 0
        var current = startDt
        while (current.isBefore(endDt)) {
            val hour = current.hour
            if (hour < 6 || hour >= 22) {
                nightMinutes++
            }
            current = current.plusMinutes(1)
        }
        return nightMinutes
    }

    fun formatNightMinutes(minutes: Int): String {
        val hours = minutes / 60
        val mins = minutes % 60
        return String.format("%d ч %02d", hours, mins)
    }
}
