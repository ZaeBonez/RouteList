package com.example.routelist.presentation.mainActivity.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.routelist.databinding.ItemRouteBinding
import com.example.routelist.presentation.mainActivity.model.RouteListItem

class RouteViewHolder(
    private val binding: ItemRouteBinding,
    private val onRouteClick: (RouteListItem.RouteItem) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RouteListItem.RouteItem) {
        binding.tvTrain.text = item.trainNumber
        binding.tvStart.text = item.start
        binding.tvEnd.text = item.end
        binding.tvHours.text = item.hours
        binding.cardViewMaterial.shapeAppearanceModel = item.routePosition.shapeAppearanceModel

        binding.root.setOnClickListener { onRouteClick(item) }
    }
}