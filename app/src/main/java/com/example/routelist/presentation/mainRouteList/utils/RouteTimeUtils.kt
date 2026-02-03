package com.example.routelist.presentation.mainRouteList.utils

import com.example.routelist.domain.RouteListInfo
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RouteTimeUtils @Inject constructor() {

    private val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")

    fun workedHours(route: RouteListInfo): Long {
        return minutesBetween(route.startDate, route.endDate)
    }

    fun passengerHours(route: RouteListInfo): Long {
        return minutesBetween(route.passengerStartDate, route.passengerEndDate)
    }

    fun nightHours(route: RouteListInfo): Long {
        return nightMinutesBetween(route.startDate, route.endDate)
    }

    fun toHoursString(totalMinutes: Long): String {
        val hours = totalMinutes / 60
        val minutes = totalMinutes % 60
        return "%d:%02d".format(hours, minutes)
    }

    fun minutesBetween(start: String?, end: String?): Long {
        val s = parse(start) ?: return 0
        val e = parse(end) ?: return 0
        return Duration.between(s, e).toMinutes()
    }

    fun nightMinutesBetween(start: String, end: String): Long {
        val s = parse(start) ?: return 0
        val e = parse(end) ?: return 0

        var current = s
        var total = 0L

        while (current.isBefore(e)) {
            val hour = current.hour
            if (hour >= 22 || hour < 6) total++
            current = current.plusMinutes(1)
        }
        return total
    }

    private fun parse(date: String?): LocalDateTime? {
        if (date.isNullOrBlank()) return null
        return try {
            LocalDateTime.parse(date, formatter)
        } catch (_: Exception) {
            null
        }
    }
}