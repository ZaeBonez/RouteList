package com.example.routelist.presentation.routeDetails.model

data class RouteDetailsState(
    val amountsDetails: AmountDetails = AmountDetails(),
    val routeArgs: RouteArgs = RouteArgs(),
    val nightTime: String = "",
)
