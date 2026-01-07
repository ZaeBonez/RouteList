package com.example.routelist.presentation.addRouteActivity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.routelist.databinding.FragmentAddRouteBinding
import com.example.routelist.presentation.addRouteActivity.model.AddRouteState
import com.example.routelist.presentation.addRouteActivity.model.CalendarPickerRouter
import com.example.routelist.presentation.mainActivity.RouteApp
import com.example.routelist.presentation.mainActivity.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject


class AddRouteFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    private var _binding: FragmentAddRouteBinding? = null
    private val binding: FragmentAddRouteBinding
        get() = _binding!!


    val viewModel: AddRouteViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AddRouteViewModel::class]
    }

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
        _binding = FragmentAddRouteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.getStateFlow().flowWithLifecycle(lifecycle).collect {
                setupDateRow(it)
                setupPassengerInfo(it)
            }
        }

        lifecycleScope.launch {
            viewModel.getErrorFlow().flowWithLifecycle(lifecycle).collect {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        addTextChangeListeners()
        setupDatePickerListeners()
        saveButton()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun addTextChangeListeners() {

        binding.etRouteNumber.doOnTextChanged { text, _, _, _ ->
            viewModel.updateRouteNumber(text.toString())
        }

        binding.etTrainNumber.doOnTextChanged { text, _, _, _ ->
            viewModel.updateTrainNumber(trainNum = text.toString())
        }

        binding.etComposition.doOnTextChanged { text, _, _, _ ->
            viewModel.updateCarriageCount(carriageCount = text.toString())
        }

        binding.etStationFrom.doOnTextChanged { text, _, _, _ ->
            viewModel.updateStartStation(startStation = text.toString())
        }

        binding.etStationTo.doOnTextChanged { text, _, _, _ ->
            viewModel.updateEndStation(endStation = text.toString())
        }

        binding.etDistance.doOnTextChanged { text, _, _, _ ->
            viewModel.updateDistance(distance = text.toString())
        }

        binding.etStops.doOnTextChanged { text, _, _, _ ->
            viewModel.updateCountStop(stopsCount = text.toString())
        }

    }

    fun setupDateRow(item: AddRouteState) {
        binding.tvStartDate.setText(item.dateRow.startDate)
        binding.tvEndDate.setText(item.dateRow.endDate)
    }

    fun setupPassengerInfo(item: AddRouteState) {
        binding.etArrivalDate.setText(item.passengerInfo.passengerStartDate)
        binding.etDepartureDate.setText(item.passengerInfo.passengerEndDate)
    }

    fun setupDatePickerListeners() {

        binding.tvStartDate.setOnClickListener {
            CalendarPickerRouter(requireContext()).show { date ->
                viewModel.updateStartDateRow(start = date)
            }
        }

        binding.tvEndDate.setOnClickListener {
            CalendarPickerRouter(requireContext()).show { date ->
                viewModel.updateEndDateRow(end = date)
            }
        }

        binding.etArrivalDate.setOnClickListener {
            CalendarPickerRouter(requireContext()).show { datePassenger ->
                viewModel.updatePassengerStartDateRow(start = datePassenger)
            }
        }

        binding.etDepartureDate.setOnClickListener {
            CalendarPickerRouter(requireContext()).show { datePassenger ->
                viewModel.updatePassengerEndDateRow(end = datePassenger)
            }
        }

    }

    private fun saveButton() {

        binding.saveRoute.setOnClickListener {

            if (viewModel.validate()) {
                viewModel.saveRoute()
                Toast.makeText(requireContext(), "Маршрут сохранён", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            }
        }
    }
}



