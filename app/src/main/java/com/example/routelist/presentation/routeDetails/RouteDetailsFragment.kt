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
import com.example.routelist.presentation.mainActivity.model.RouteListItem
import com.example.routelist.presentation.routeDetails.model.RouteArgs
import com.example.routelist.presentation.routeDetails.model.RouteDetailsState
import kotlinx.coroutines.launch

class RouteDetailsFragment : BaseFragment<FragmentRouteDetailsBinding, RouteDetailsViewModel>() {

    override fun inject() {
        component.inject(this)
    }

    override val viewModelClass = RouteDetailsViewModel::class


    override fun fragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRouteDetailsBinding =
        FragmentRouteDetailsBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = readArgs()

        // Дубликат
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getStateFlow().collect { state ->
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

        binding.btnBack.setOnClickListener { viewModel.routeBack() }

        binding.btnClose.setOnClickListener { viewModel.routeBack() }

        binding.btnDelete.setOnClickListener {
            viewModel.deleteRoute(routeId)

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