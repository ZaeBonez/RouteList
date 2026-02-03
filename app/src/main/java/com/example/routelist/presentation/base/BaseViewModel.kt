package com.example.routelist.presentation.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<STATE : Any, EFFECT : Any>(initState: STATE) : ViewModel() {

    protected open val router: BaseRouter? = null

    protected val state = MutableStateFlow(initState)

    protected val effects = MutableSharedFlow<EFFECT>()

    fun getStateFlow(): StateFlow<STATE> = state

    fun getEffectFlow(): MutableSharedFlow<EFFECT> = effects

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