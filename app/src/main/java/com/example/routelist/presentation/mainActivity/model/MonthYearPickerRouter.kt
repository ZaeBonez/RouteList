package com.example.routelist.presentation.mainActivity.model

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.routelist.databinding.MonthYearPickerBinding
import java.util.Calendar

class MonthYearPickerRouter(
    private val context: Context
) {

    fun show(
        initialMonth: Int,
        initialYear: Int,
        onPicked: (monthName: String, month: Int, year: Int) -> Unit,

        ) {

        val binding = MonthYearPickerBinding.inflate(LayoutInflater.from(context))

        val current = Calendar.getInstance()

        binding.npMonth.minValue = 1
        binding.npMonth.maxValue = 12
        binding.npMonth.value = initialMonth + 1

        binding.npYear.minValue = 2000
        binding.npYear.maxValue = 2100
        binding.npYear.value = initialYear

        AlertDialog.Builder(context)
            .setTitle("Выберите месяц и год")
            .setView(binding.root)
            .setPositiveButton("OK") { _, _ ->

                val month = binding.npMonth.value - 1
                val year = binding.npYear.value

                val monthName = getMonthName(month)

                onPicked(monthName, month, year)
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    fun getMonthName(monthZeroBased: Int): String = months[monthZeroBased]

    private val months = arrayOf(
        "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
        "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
    )
}
