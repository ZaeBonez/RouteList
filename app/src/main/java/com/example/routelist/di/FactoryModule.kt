package com.example.routelist.di

import com.example.routelist.presentation.mainRouteList.DefaultRouteListFactory
import com.example.routelist.presentation.mainRouteList.RouteListFactory
import dagger.Binds
import dagger.Module

@Module
interface FactoryModule {

    @Binds
    fun bindRouteListFactory(routeFactoryImpl: DefaultRouteListFactory): RouteListFactory

}