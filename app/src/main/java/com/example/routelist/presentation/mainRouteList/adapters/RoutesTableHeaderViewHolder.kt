package com.example.routelist.presentation.mainRouteList.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.routelist.databinding.ItemRouteTableHeaderBinding
import com.example.routelist.presentation.mainRouteList.model.RouteListItem
import com.example.routelist.presentation.mainRouteList.model.RoutePosition

class RoutesTableHeaderViewHolder(
    private val binding: ItemRouteTableHeaderBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RouteListItem.RoutesTableHeaders){
        binding.headerTrain.text = "Поезд"
        binding.headerStart.text = "Явка"
        binding.headerEnd.text = "Окончание"
        binding.headerHours.text = "Часы"
        binding.cardViewMaterial.shapeAppearanceModel = RoutePosition.First.shapeAppearanceModel
    }
}