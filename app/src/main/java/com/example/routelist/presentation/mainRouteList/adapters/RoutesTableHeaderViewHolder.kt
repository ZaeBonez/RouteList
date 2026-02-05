package com.example.routelist.presentation.mainRouteList.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.routelist.R
import com.example.routelist.databinding.ItemRouteTableHeaderBinding
import com.example.routelist.presentation.mainRouteList.model.RouteListItem
import com.example.routelist.presentation.mainRouteList.model.RoutePosition

class RoutesTableHeaderViewHolder(
    private val binding: ItemRouteTableHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RouteListItem.RoutesTableHeaders) {
        binding.headerTrain.setText(R.string.train)
        binding.headerStart.setText(R.string.appearance)
        binding.headerEnd.setText(R.string.finish)
        binding.headerHours.setText(R.string.hours)
        binding.cardViewMaterial.shapeAppearanceModel = RoutePosition.First.shapeAppearanceModel
    }
}