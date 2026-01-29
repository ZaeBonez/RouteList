package com.example.routelist.presentation.addRouteActivity.model

import androidx.annotation.StringRes

sealed interface AddRouteEffect {

    data class ShowToast(@field:StringRes val message: Int) : AddRouteEffect

    data object NavigateBackStack : AddRouteEffect
}