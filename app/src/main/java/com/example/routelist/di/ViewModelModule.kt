package com.example.routelist.di

import androidx.lifecycle.ViewModel
import com.example.routelist.presentation.addRouteActivity.AddRouteViewModel
import com.example.routelist.presentation.mainActivity.RouteViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ChainModule::class])
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AddRouteViewModel::class)
    fun bindAddRouteViewModel(viewModel: AddRouteViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RouteViewModel::class)
    fun bindRouteViewModel(viewModel: RouteViewModel): ViewModel

}