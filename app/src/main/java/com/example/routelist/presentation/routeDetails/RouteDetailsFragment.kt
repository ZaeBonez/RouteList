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
import com.example.routelist.presentation.mainActivity.RouteViewModel
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

        val routeId = requireArguments().getInt(ARG_ID)
        val trainNumber = arguments?.getString(ARG_TRAIN_NUMBER).orEmpty()
        val start = arguments?.getString(ARG_START).orEmpty()
        val end = arguments?.getString(ARG_END).orEmpty()
        val hours = arguments?.getString(ARG_HOURS).orEmpty()

        if (trainNumber.isNotBlank()) {
            binding.tvTitleNumber.text = "Маршрут №$trainNumber"
        }
        if (hours.isNotBlank()) {
            binding.tvWorkedTime.text = hours
        }
        if (start.isNotBlank()) {
            binding.tvRouteStart.text = start
        }
        if (end.isNotBlank()) {
            binding.tvRouteEnd.text = end
        }

        val nightMins = NightHoursCalculator().calculateNightMinutes(start,end)
        binding.tvNightTime.text = NightHoursCalculator().formatNightMinutes(nightMins)

        binding.tvNightAmount.text = "0 ₽"
        binding.tvTotalAmount.text = "0 ₽"
        binding.tvHourlyAmount.text = "0 ₽"
        binding.tvPassengerAmount.text = "0 ₽"
        binding.tvSummaryAmount.text = "0 ₽"

        binding.btnBack.setOnClickListener { parentFragmentManager.popBackStack() }
        binding.btnClose.setOnClickListener { parentFragmentManager.popBackStack() }
        binding.btnDelete.setOnClickListener {
            val id = routeId ?: return@setOnClickListener
            viewModel.deleteRoute(id)
            parentFragmentManager.popBackStack()
        }
        binding.btnEdit.setOnClickListener {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        const val ARG_ID = "route_id"
        const val ARG_TRAIN_NUMBER = "train_number"
        const val ARG_START = "start"
        const val ARG_END = "end"
        const val ARG_HOURS = "hours"
    }
}