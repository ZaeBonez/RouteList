package com.example.routelist.presentation.mainActivity

import androidx.fragment.app.FragmentManager

abstract class BaseRouter {

    protected var parentFragmentManager: FragmentManager? = null

    fun attach(parentFragmentManager: FragmentManager) {
        this.parentFragmentManager = parentFragmentManager
    }

    fun detach() {
        this.parentFragmentManager = null
    }

    protected fun startTransaction(transaction: FragmentManager.() -> Unit) {
        parentFragmentManager?.transaction()
    }
}