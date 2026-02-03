package com.example.routelist.presentation.mainRouteList

import java.util.Calendar
import javax.inject.Inject

class DateProvider @Inject constructor() {

    fun getCurrentPeriod(): Pair<String, String> {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR).toString()
        val month = (cal.get(Calendar.MONTH) + 1).toString().padStart(2, '0')
        return year to month
    }

    fun getCurrentHeader(): Pair<Int, Int> {
        val cal = Calendar.getInstance()
        val month0 = cal.get(Calendar.MONTH)
        val year = cal.get(Calendar.YEAR)
        return month0 to year
    }
}