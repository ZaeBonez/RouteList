package com.example.routelist.presentation.mainActivity.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.routelist.databinding.ItemHeaderBinding
import com.example.routelist.databinding.ItemRouteBinding
import com.example.routelist.databinding.ItemRouteTableHeaderBinding
import com.example.routelist.databinding.ItemRoutesHeaderBinding
import com.example.routelist.databinding.ItemStatisticCardBinding
import com.example.routelist.presentation.mainActivity.model.MonthYearPickerRouter
import com.example.routelist.presentation.mainActivity.model.RouteListItem

class RouteListAdapter(
    private val router: MonthYearPickerRouter,
    private val onMonthYearPicked: (month: Int, year: Int) -> Unit
) : ListAdapter<RouteListItem, RecyclerView.ViewHolder>(RouteInfoDiffCallback()) {

    companion object {
        internal const val CALENDAR_HEADER = 0
        internal const val CARD_INFO = 1
        internal const val ROUTES_HEADER = 2
        internal const val ROUTES_TABLE_HEADER = 4
        internal const val ROUTE_LIST = 3
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is RouteListItem.CalendarHeader -> CALENDAR_HEADER
        RouteListItem.RoutesHeader -> ROUTES_HEADER
        is RouteListItem.Card -> CARD_INFO
        is RouteListItem.RouteItem -> ROUTE_LIST
        RouteListItem.RoutesTableHeaders -> ROUTES_TABLE_HEADER
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            CALENDAR_HEADER -> CalendarViewHolder(
                ItemHeaderBinding.inflate(inflater, parent, false),
                router,
                onMonthYearPicked
            )

            CARD_INFO -> CardViewHolder(
                ItemStatisticCardBinding.inflate(inflater, parent, false)
            )

            ROUTES_HEADER -> RoutesHeaderViewHolder(
                ItemRoutesHeaderBinding.inflate(inflater, parent, false)
            )

            ROUTE_LIST -> RouteViewHolder(
                ItemRouteBinding.inflate(inflater, parent, false)
            )

            ROUTES_TABLE_HEADER -> RoutesTableHeaderViewHolder(
                ItemRouteTableHeaderBinding.inflate(inflater, parent, false)
            )

            else -> throw IllegalStateException("Unknown viewType")
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (val item = getItem(position)) {
            is RouteListItem.CalendarHeader -> (holder as CalendarViewHolder).bind(item)
            is RouteListItem.Card -> (holder as CardViewHolder).bind(item)
            is RouteListItem.RoutesHeader -> (holder as RoutesHeaderViewHolder).bind()
            is RouteListItem.RouteItem -> (holder as RouteViewHolder).bind(item)
            is RouteListItem.RoutesTableHeaders -> (holder as RoutesTableHeaderViewHolder).bind(item)
        }
    }

    fun populate(newItems: List<RouteListItem>) {
        submitList(newItems)
    }

    fun getCardSpanSize(cardCount: Int) = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return when (getItemViewType(position)) {
                CALENDAR_HEADER, ROUTES_HEADER, ROUTE_LIST -> cardCount
                CARD_INFO -> 1
                else -> cardCount
            }
        }
    }
}