package com.example.routelist.presentation.mainActivity.router

import com.example.routelist.R
import com.example.routelist.presentation.mainActivity.BaseRouter
import com.example.routelist.presentation.mainActivity.model.RouteListItem
import com.example.routelist.presentation.routeDetails.RouteDetailsFragment
import javax.inject.Inject

class MainRouter @Inject constructor() : BaseRouter() {

    fun openRouteDetails(routeItem: RouteListItem.RouteItem) {
        val fragment = RouteDetailsFragment.newInstance(routeItem)

        startTransaction {
            beginTransaction()
                .replace(R.id.route_list_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}

