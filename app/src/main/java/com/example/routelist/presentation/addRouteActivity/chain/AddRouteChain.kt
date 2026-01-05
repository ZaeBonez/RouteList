package com.example.routelist.presentation.addRouteActivity.chain

interface AddRouteChain {

    fun validate(model: AddRouteChainModel): Int?

    abstract class Base : AddRouteChain {

        protected open val newChain: AddRouteChain? = null

        override fun validate(model: AddRouteChainModel): Int? {
            return if (isInvalid(model)) {
                getErrorText()
            } else {
                newChain?.validate(model)
            }
        }

        protected abstract fun isInvalid(model: AddRouteChainModel): Boolean

        protected abstract fun getErrorText(): Int
    }
}