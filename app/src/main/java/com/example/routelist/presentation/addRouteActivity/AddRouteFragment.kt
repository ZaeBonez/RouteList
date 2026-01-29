package com.example.routelist.presentation.addRouteActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.routelist.databinding.FragmentAddRouteBinding
import com.example.routelist.presentation.addRouteActivity.model.AddRouteEffect
import com.example.routelist.presentation.addRouteActivity.model.AddRouteState
import com.example.routelist.presentation.addRouteActivity.router.CalendarPickerRouter
import com.example.routelist.presentation.mainActivity.base.BaseFragment
import kotlinx.coroutines.launch
import kotlin.reflect.KClass


class AddRouteFragment : BaseFragment<FragmentAddRouteBinding, AddRouteViewModel>() {

    override fun inject() {
        component.inject(this)
    }

    override val viewModelClass: KClass<AddRouteViewModel> = AddRouteViewModel::class

    override fun fragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddRouteBinding = FragmentAddRouteBinding.inflate(
        inflater,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Вынести дублирующийся код
        lifecycleScope.launch {
            viewModel.getEffectFlow().flowWithLifecycle(lifecycle).collect {
                when (it) {
                    is AddRouteEffect.ShowToast -> Toast.makeText(
                        requireContext(),
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    is AddRouteEffect.NavigateBackStack -> viewModel.routerBack()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.getStateFlow().flowWithLifecycle(lifecycle).collect {
                setupDateRow(it)
                setupPassengerInfo(it)
            }
        }

        addTextChangeListeners()
        setupDatePickerListeners()
        saveButton()

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

        binding.etPassengerTrainNumber.doOnTextChanged { text, _, _, _ ->
            viewModel.updatePassengerNumber(number = text.toString())
        }

    }

    fun setupDateRow(item: AddRouteState) {
        binding.tvStartDate.setText(item.dateRow.startDate)
        binding.tvEndDate.setText(item.dateRow.endDate)
    }

    fun setupPassengerInfo(item: AddRouteState) {
        binding.etPassengerTrainNumber.setText(item.passengerInfo.passengerTrainNumber)
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
            viewModel.saveRouteV2()
        }
    }
}



