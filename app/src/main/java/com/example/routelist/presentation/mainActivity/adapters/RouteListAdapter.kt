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
import com.example.routelist.presentation.mainActivity.adapters.diff.RouteInfoDiffCallback
import com.example.routelist.presentation.mainActivity.model.RouteListItem
import com.example.routelist.presentation.mainActivity.model.SpanType
import com.example.routelist.presentation.mainActivity.router.MonthYearPickerRouter

private const val DEFAULT_SPAN_SIZE = 2

class RouteListAdapter(
    private val router: MonthYearPickerRouter,
    private val onMonthYearPicked: (month: Int, year: Int) -> Unit,
    private val onRouteClick: (RouteListItem.RouteItem) -> Unit
) : ListAdapter<RouteListItem, RecyclerView.ViewHolder>(RouteInfoDiffCallback()) {

    fun getSpanSize(position: Int): Int {
        return (getItem(position) as? SpanType)?.spanSize ?: DEFAULT_SPAN_SIZE
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is RouteListItem.CalendarHeader -> RouteListViewType.CALENDAR_HEADER.ordinal
        is RouteListItem.Card -> RouteListViewType.CARD_INFO.ordinal
        RouteListItem.RoutesHeader -> RouteListViewType.ROUTES_HEADER.ordinal
        is RouteListItem.RouteItem -> RouteListViewType.ROUTE_ITEM.ordinal
        RouteListItem.RoutesTableHeaders -> RouteListViewType.ROUTES_TABLE_HEADER.ordinal
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            RouteListViewType.CALENDAR_HEADER.ordinal -> CalendarViewHolder(
                ItemHeaderBinding.inflate(inflater, parent, false),
                router,
                onMonthYearPicked
            )

            RouteListViewType.CARD_INFO.ordinal -> CardViewHolder(
                ItemStatisticCardBinding.inflate(inflater, parent, false)
            )

            RouteListViewType.ROUTES_HEADER.ordinal -> RoutesHeaderViewHolder(
                ItemRoutesHeaderBinding.inflate(inflater, parent, false)
            )

            RouteListViewType.ROUTE_ITEM.ordinal -> RouteViewHolder(
                ItemRouteBinding.inflate(inflater, parent, false),
                onRouteClick
            )

            RouteListViewType.ROUTES_TABLE_HEADER.ordinal -> RoutesTableHeaderViewHolder(
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
                RouteListViewType.CALENDAR_HEADER.ordinal, RouteListViewType.ROUTES_HEADER.ordinal, RouteListViewType.ROUTE_ITEM.ordinal -> cardCount
                RouteListViewType.CARD_INFO.ordinal -> 1
                else -> cardCount
            }
        }
    }
}