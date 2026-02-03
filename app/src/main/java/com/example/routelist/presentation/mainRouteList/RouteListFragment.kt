package com.example.routelist.presentation.mainRouteList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.routelist.databinding.FragmentMainBinding
import com.example.routelist.presentation.mainRouteList.adapters.RouteListAdapter
import com.example.routelist.presentation.base.BaseFragment
import com.example.routelist.presentation.mainRouteList.router.MonthYearPickerRouter
import kotlin.reflect.KClass

class RouteListFragment : BaseFragment<FragmentMainBinding, RouteViewModel, RouteListState, Any>() {

    override fun inject() {
        component.inject(this)
    }


    override val viewModelClass: KClass<RouteViewModel> = RouteViewModel::class

    private lateinit var monthYearPicker: MonthYearPickerRouter
    private lateinit var adapter: RouteListAdapter

    override fun fragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMainBinding =
        FragmentMainBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        monthYearPicker = MonthYearPickerRouter(requireContext())

        setupRecyclerView()

        binding.addNewRoute.setOnClickListener {
            viewModel.openAddRoute()
        }

    }

    private fun setupRecyclerView() {

        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvMain.layoutManager = layoutManager

        adapter = RouteListAdapter(
            router = monthYearPicker,
            onMonthYearPicked = { monthZeroBased, year ->
                viewModel.setMonthYear(monthZeroBased, year)
            },
            onRouteClick = { routeItem ->
                viewModel.openRouteDetails(routeItem)
            }
        )

        binding.rvMain.adapter = adapter
        (binding.rvMain.itemAnimator as? androidx.recyclerview.widget.SimpleItemAnimator)
            ?.supportsChangeAnimations = false

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = adapter.getSpanSize(position)
        }
    }

    override fun observeState(state: RouteListState) {
        adapter.submitList(state.items)
    }


    override fun observeEffect(effect: Any) {
        TODO()
    }


}