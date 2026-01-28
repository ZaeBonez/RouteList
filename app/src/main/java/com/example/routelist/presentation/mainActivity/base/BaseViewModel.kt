package com.example.routelist.presentation.mainActivity.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<STATE : Any>(initState: STATE) : ViewModel() {

    protected open val router: BaseRouter? = null

    protected val state = MutableStateFlow(initState)

    fun getState(): StateFlow<STATE> = state

    fun attach(fragment: Fragment) {
        router?.attach(fragment.parentFragmentManager)
    }

    fun detach() {
        router?.detach()
    }

    protected fun setState(setNewState: STATE.() -> STATE) {
        state.value = state.value.setNewState()
    }
}