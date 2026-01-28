package com.example.routelist.presentation.mainActivity.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.routelist.databinding.ItemHeaderBinding
import com.example.routelist.presentation.mainActivity.router.MonthYearPickerRouter
import com.example.routelist.presentation.mainActivity.model.RouteListItem

class CalendarViewHolder(
    private val binding: ItemHeaderBinding,
    private val router: MonthYearPickerRouter,
    private val onMonthYearPicked: (monthZeroBased: Int, year: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RouteListItem.CalendarHeader) {
        val monthName = router.getMonthName(item.month)
        binding.tvCalendarHeader.text = "$monthName ${item.year}" // имя поля поменяй под свой layout

        binding.root.setOnClickListener {
            router.show(
                initialMonth = item.month, // 0..11
                initialYear = item.year
            ) { _, monthZeroBased, year ->
                onMonthYearPicked(monthZeroBased, year)
            }
        }
    }
}