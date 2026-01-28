package com.example.routelist.di

import android.app.Application
import com.example.routelist.data.database.AppDatabase
import com.example.routelist.data.database.RouteInfoDao
import com.example.routelist.data.repository.RouteRepositoryImpl
import com.example.routelist.domain.RouteRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    fun bindRouteRepository(impl: RouteRepositoryImpl): RouteRepository

    companion object {
        @Provides
        fun provideRouteInfoDao(
            application: Application
        ): RouteInfoDao {
            return AppDatabase.getInstance(application).routeInfoDao()
        }
    }
}