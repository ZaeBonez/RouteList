package com.example.routelist.presentation.routeDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.routelist.databinding.FragmentRouteDetailsBinding
import com.example.routelist.presentation.mainActivity.base.BaseFragment
import com.example.routelist.presentation.mainActivity.RouteApp
import com.example.routelist.presentation.mainActivity.model.RouteListItem
import com.example.routelist.presentation.routeDetails.model.RouteArgs
import com.example.routelist.presentation.routeDetails.model.RouteDetailsState
import kotlinx.coroutines.launch

class RouteDetailsFragment : BaseFragment<FragmentRouteDetailsBinding, RouteDetailsViewModel>() {


    private val component by lazy {
        (requireActivity().application as RouteApp).component
    }

    override fun inject() {
        component.inject(this)
    }

    override val viewModelClass: Class<RouteDetailsViewModel> = RouteDetailsViewModel::class.java


    override fun fragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRouteDetailsBinding =
        FragmentRouteDetailsBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = readArgs()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getState().collect { state ->
                    render(state)
                }
            }
        }

        viewModel.init(args)

        setupClicks(args.routeId)
    }


    private fun render(state: RouteDetailsState) = with(binding) {
        val a = state.routeArgs

        tvTitleNumber.text = "Маршрут №${a.trainNumber}"
        tvWorkedTime.text = a.hours
        tvRouteStart.text = a.start
        tvRouteEnd.text = a.end
        tvNightTime.text = state.nightTime

        tvNightAmount.text = state.amountsDetails.nightAmount
        tvTotalAmount.text = state.amountsDetails.totalAmount
        tvHourlyAmount.text = state.amountsDetails.hourlyAmount
        tvPassengerAmount.text = state.amountsDetails.passengerAmount
        tvSummaryAmount.text = state.amountsDetails.summaryAmount
    }


    private fun readArgs(): RouteArgs = RouteArgs(
        routeId = requireArguments().getInt("route_id"),
        trainNumber = requireArguments().getString("train_number").orEmpty(),
        start = requireArguments().getString("start").orEmpty(),
        end = requireArguments().getString("end").orEmpty(),
        hours = requireArguments().getString("hours").orEmpty(),
    )

    private fun setupClicks(routeId: Int) {

        binding.btnBack.setOnClickListener { parentFragmentManager.popBackStack() }

        binding.btnClose.setOnClickListener { parentFragmentManager.popBackStack() }

        binding.btnDelete.setOnClickListener {
            viewModel.deleteRoute(routeId)
            parentFragmentManager.popBackStack()
        }

        binding.btnEdit.setOnClickListener {
            TODO()
        }
    }


    companion object {
        fun newInstance(item: RouteListItem.RouteItem) = RouteDetailsFragment().apply {
            arguments = Bundle().apply {
                putInt("route_id", item.id)
                putString("train_number", item.trainNumber)
                putString("start", item.start)
                putString("end", item.end)
                putString("hours", item.hours)
            }
        }
    }
}