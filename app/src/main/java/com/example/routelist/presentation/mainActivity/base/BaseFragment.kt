package com.example.routelist.presentation.mainActivity.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.routelist.presentation.mainActivity.RouteApp
import com.example.routelist.presentation.mainActivity.sl.ViewModelFactory
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseFragment<V : ViewBinding, T : BaseViewModel<*, *>> : Fragment() {

    protected val component by lazy {
        (requireActivity().application as RouteApp).component
    }

    protected abstract fun inject()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    protected abstract val viewModelClass: KClass<T>
    protected val viewModel: T by lazy { ViewModelProvider(this, viewModelFactory)[viewModelClass] }

    private var _binding: V? = null
    protected val binding get() = _binding!!


    protected abstract fun fragmentBinding(inflater: LayoutInflater, container: ViewGroup?): V

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.attach(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = fragmentBinding(inflater, container)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.detach()
        _binding = null
    }
}