package com.example.routelist.presentation.mainActivity.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.routelist.presentation.mainActivity.sl.ViewModelFactory
import javax.inject.Inject

abstract class BaseFragment<V : ViewBinding, T : BaseViewModel<*>> : Fragment() {

    protected abstract fun inject()


    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    protected abstract val viewModelClass: Class<T>
    protected lateinit var viewModel: T
        private set

    private var _binding: V? = null
    protected val binding get() = _binding!!


    protected abstract fun fragmentBinding(inflater: LayoutInflater, container: ViewGroup?): V

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[viewModelClass]
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