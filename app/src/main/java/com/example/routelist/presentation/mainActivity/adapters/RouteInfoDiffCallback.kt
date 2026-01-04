package com.example.routelist.presentation.mainActivity.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.routelist.presentation.mainActivity.model.RouteListItem

class RouteInfoDiffCallback : DiffUtil.ItemCallback<RouteListItem>() {

    override fun areItemsTheSame(
        oldItem: RouteListItem,
        newItem: RouteListItem
    ): Boolean {
        return when {
            oldItem is RouteListItem.CalendarHeader && newItem is RouteListItem.CalendarHeader -> true
            oldItem is RouteListItem.RoutesHeader && newItem is RouteListItem.RoutesHeader -> true
            oldItem is RouteListItem.RoutesTableHeaders && newItem is RouteListItem.RoutesTableHeaders -> true

            oldItem is RouteListItem.Card && newItem is RouteListItem.Card ->
                oldItem.title == newItem.title

            oldItem is RouteListItem.RouteItem && newItem is RouteListItem.RouteItem ->
                oldItem.trainNumber == newItem.trainNumber &&
                        oldItem.start == newItem.start &&
                        oldItem.end == newItem.end

            else -> false
        }
    }

    override fun areContentsTheSame(
        oldItem: RouteListItem,
        newItem: RouteListItem
    ): Boolean {
        return oldItem == newItem
    }
}