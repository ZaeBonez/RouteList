package com.example.routelist.di

import android.app.Application
import com.example.routelist.presentation.addRouteActivity.AddRouteFragment
import com.example.routelist.presentation.mainActivity.RouteListFragment
import com.example.routelist.presentation.routeDetails.RouteDetailsFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class]
)
interface ApplicationComponent {

    fun inject(fragment: AddRouteFragment)

    fun inject(fragment: RouteListFragment)

    fun inject(fragment: RouteDetailsFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}