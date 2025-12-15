package com.example.routelist.di

import com.example.routelist.presentation.addRouteActivity.chain.AddRouteChain
import com.example.routelist.presentation.addRouteActivity.chain.CarriageCountChain
import com.example.routelist.presentation.addRouteActivity.chain.EndDateChain
import com.example.routelist.presentation.addRouteActivity.chain.EndStationChain
import com.example.routelist.presentation.addRouteActivity.chain.NumberChain
import com.example.routelist.presentation.addRouteActivity.chain.StartDateChain
import com.example.routelist.presentation.addRouteActivity.chain.StartStationChain
import com.example.routelist.presentation.addRouteActivity.chain.TrainNumberChain
import dagger.Module
import dagger.Provides

@Module
 class ChainModule {

    @Provides
    fun provideGetChain(): AddRouteChain {
        return NumberChain(
            StartDateChain(
                EndDateChain(
                    TrainNumberChain(
                        CarriageCountChain(
                            StartStationChain(
                                EndStationChain()
                            )
                        )
                    )
                )
            )
        )
    }
}