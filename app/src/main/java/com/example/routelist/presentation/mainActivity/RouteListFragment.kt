package com.example.routelist.presentation.mainActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.routelist.databinding.FragmentMainBinding
import com.example.routelist.presentation.mainActivity.adapters.RouteListAdapter
import com.example.routelist.presentation.mainActivity.base.BaseFragment
import com.example.routelist.presentation.mainActivity.router.MonthYearPickerRouter


class RouteListFragment : BaseFragment<FragmentMainBinding, RouteViewModel>() {

    private val component by lazy {
        (requireActivity().application as RouteApp).component
    }

    override fun inject() {
        component.inject(this)
    }

    override val viewModelClass: Class<RouteViewModel> = RouteViewModel::class.java

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

        observeViewModel()

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
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    RouteListAdapter.CARD_INFO -> 1 // вынести в енам класс и .ordinal
                    else -> 2
                }
            }
        }
    }

    private fun observeViewModel() {
        viewModel.items.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
    }
}