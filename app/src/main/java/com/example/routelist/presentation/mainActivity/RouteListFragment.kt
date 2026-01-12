package com.example.routelist.presentation.mainActivity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.routelist.R
import com.example.routelist.databinding.FragmentMainBinding
import com.example.routelist.presentation.addRouteActivity.AddRouteFragment
import com.example.routelist.presentation.mainActivity.adapters.RouteListAdapter
import com.example.routelist.presentation.mainActivity.model.MonthYearPickerRouter
import com.example.routelist.presentation.mainActivity.model.RouteListItem
import com.example.routelist.presentation.routeDetails.RouteDetailsFragment
import javax.inject.Inject


class RouteListFragment : Fragment() {

    private lateinit var viewModel: RouteViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var monthYearPicker: MonthYearPickerRouter


    private lateinit var adapter: RouteListAdapter

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!


    private val component by lazy {
        (requireActivity().application as RouteApp).component
    }


    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[RouteViewModel::class]

        monthYearPicker = MonthYearPickerRouter(requireContext())

        setupRecyclerView()
        observeViewModel()


        binding.addNewRoute.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.route_list_container, AddRouteFragment())
                .addToBackStack(null)
                .commit()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
                openRouteDetails(routeItem)
            }
        )

        binding.rvMain.adapter = adapter
        (binding.rvMain.itemAnimator as? androidx.recyclerview.widget.SimpleItemAnimator)
            ?.supportsChangeAnimations = false

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    RouteListAdapter.CARD_INFO -> 1
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

    private fun openRouteDetails(routeItem: RouteListItem.RouteItem) {
        val bundle = Bundle().apply {
            putInt("route_id", routeItem.id)
            putString("train_number", routeItem.trainNumber)
            putString("start", routeItem.start)
            putString("end", routeItem.end)
            putString("hours", routeItem.hours)
        }

        val detailsFragment = RouteDetailsFragment().apply {
            arguments = bundle
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.route_list_container, detailsFragment)
            .addToBackStack(null)
            .commit()
    }

}

