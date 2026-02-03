package com.example.routelist.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.routelist.presentation.mainRouteList.RouteApp
import com.example.routelist.presentation.mainRouteList.sl.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseFragment<V : ViewBinding, T : BaseViewModel<STATE, EFFECT>, STATE : Any, EFFECT : Any> :
    Fragment() {

    protected val component by lazy {
        (requireActivity().application as RouteApp).component
    }

    protected abstract fun inject()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    protected abstract fun observeState(state: STATE)

    protected abstract fun observeEffect(effect: EFFECT)

    protected abstract val viewModelClass: KClass<T>
    protected val viewModel: T by lazy { ViewModelProvider(this, viewModelFactory)[viewModelClass] }

    private var _binding: V? = null
    protected val binding get() = _binding!!


    protected abstract fun fragmentBinding(inflater: LayoutInflater, container: ViewGroup?): V

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = fragmentBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.getStateFlow().flowWithLifecycle(lifecycle).collect { state ->
                observeState(state)
            }
        }

        lifecycleScope.launch {
            viewModel.getEffectFlow().flowWithLifecycle(lifecycle).collect { effect ->
               observeEffect(effect)
            }
        }

        viewModel.attach(this)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.detach()
        _binding = null
    }
}