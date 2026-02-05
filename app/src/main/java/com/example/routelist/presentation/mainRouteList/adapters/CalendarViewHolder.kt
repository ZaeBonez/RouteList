package com.example.routelist.presentation.mainRouteList.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.routelist.databinding.ItemHeaderBinding
import com.example.routelist.presentation.mainRouteList.model.RouteListItem
import com.example.routelist.presentation.mainRouteList.router.MonthYearPickerRouter

class CalendarViewHolder(
    private val binding: ItemHeaderBinding,
    private val router: MonthYearPickerRouter,
    private val onMonthYearPicked: (monthZeroBased: Int, year: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RouteListItem.CalendarHeader) {
        val monthName = router.getMonthName(item.month)
        binding.tvCalendarHeader.text = "$monthName ${item.year}"

        binding.root.setOnClickListener {
            router.show(
                initialMonth = item.month,
                initialYear = item.year
            ) { _, monthZeroBased, year ->
                onMonthYearPicked(monthZeroBased, year)
            }
        }
    }
}