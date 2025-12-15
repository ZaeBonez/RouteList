package com.example.routelist.presentation.addRouteActivity.chain

interface AddRouteChain {

    fun validate(model: AddRouteChainModel): String?

    abstract class Base : AddRouteChain {

        protected open val newChain: AddRouteChain? = null

        override fun validate(model: AddRouteChainModel): String? {
            return if(isInvalid(model)) {
                getErrorText()
            } else {
                newChain?.validate(model)
            }
        }

        protected abstract fun isInvalid(model: AddRouteChainModel): Boolean

        protected abstract fun getErrorText(): String // Должно быть Int т.к ты должен выдавать ссылку на ресурсы и в ui преобразовывать в string (R.string.vlad_the_bull)
    }
}