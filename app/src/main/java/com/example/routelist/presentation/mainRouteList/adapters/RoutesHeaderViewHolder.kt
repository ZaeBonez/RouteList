package com.example.routelist.presentation.mainRouteList.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.routelist.databinding.ItemRoutesHeaderBinding

class RoutesHeaderViewHolder(private val binding: ItemRoutesHeaderBinding) :
    RecyclerView.ViewHolder(binding.root), CoreViewHolder {

    override fun bind() {
        binding.tvRoutesHeader.text = "Маршрутные листы"
    }
}