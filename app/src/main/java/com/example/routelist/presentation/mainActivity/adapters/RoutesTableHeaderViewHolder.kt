package com.example.routelist.presentation.mainActivity.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.routelist.databinding.ItemRouteTableHeaderBinding
import com.example.routelist.presentation.mainActivity.model.RouteListItem

class RoutesTableHeaderViewHolder(
    private val binding: ItemRouteTableHeaderBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RouteListItem.RoutesTableHeaders){
        binding.headerTrain.text = "Поезд"
        binding.headerStart.text = "Явка"
        binding.headerEnd.text = "Окончание"
        binding.headerHours.text = "Часы"
        binding.cardViewMaterial.shapeAppearanceModel = item.routePosition.shapeAppearanceModel
    }
}