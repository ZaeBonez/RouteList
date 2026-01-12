package com.example.routelist.presentation.routeDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.routelist.domain.DeleteRouteUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class RouteDetailsViewModel @Inject constructor(
    private val deleteRouteUseCase: DeleteRouteUseCase
) : ViewModel() {

    fun deleteRoute(id: Int) {
        viewModelScope.launch {
            deleteRouteUseCase(id)
        }
    }
}