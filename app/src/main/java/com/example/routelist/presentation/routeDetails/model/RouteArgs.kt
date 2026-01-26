package com.example.routelist.presentation.routeDetails.model

data class RouteArgs(
    val routeId: Int = 0,
    val trainNumber: String = "",
    val start: String = "",
    val end: String = "",
    val hours: String = "",
)