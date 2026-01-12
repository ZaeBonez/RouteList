package com.example.routelist.presentation.routeDetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.routelist.databinding.FragmentRouteDetailsBinding
import com.example.routelist.presentation.mainActivity.RouteApp
import com.example.routelist.presentation.mainActivity.ViewModelFactory
import com.example.routelist.presentation.routeDetails.utils.NightHoursCalculator
import javax.inject.Inject

class RouteDetailsFragment : Fragment() {

    private lateinit var viewModel: RouteDetailsViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var _binding: FragmentRouteDetailsBinding? = null
    private val binding: FragmentRouteDetailsBinding
        get() = _binding!!

    private val component by lazy {
        (requireActivity().application as RouteApp).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRouteDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[RouteDetailsViewModel::class]

        val routeId = requireArguments().getInt("route_id")
        val trainNumber = arguments?.getString("train_number").orEmpty()
        val start = arguments?.getString("start").orEmpty()
        val end = arguments?.getString("end").orEmpty()
        val hours = arguments?.getString("hours").orEmpty()

        bindRouteInfo(trainNumber, start, end, hours)
        bindNightTime(start, end)
        bindAmountsZero()
        setupClicks(routeId)
    }

    private fun bindRouteInfo(trainNumber: String, start: String, end: String, hours: String) {
        binding.tvTitleNumber.text = "Маршрут №$trainNumber"
        binding.tvWorkedTime.text = hours
        binding.tvRouteStart.text = start
        binding.tvRouteEnd.text = end
    }

    private fun bindNightTime(start: String, end: String) {
        val nightMins = NightHoursCalculator.calculateNightMinutes(start, end)
        binding.tvNightTime.text = NightHoursCalculator.formatNightMinutes(nightMins)
    }

    private fun bindAmountsZero() = with(binding) {
        tvNightAmount.text = "0 ₽"
        tvTotalAmount.text = "0 ₽"
        tvHourlyAmount.text = "0 ₽"
        tvPassengerAmount.text = "0 ₽"
        tvSummaryAmount.text = "0 ₽"
    }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}