package org.softeer.robocar.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.softeer.robocar.data.repository.CarPool.CarPoolRemoteDataSource
import org.softeer.robocar.data.repository.CarPool.CarPoolRemoteDataSourceImpl
import org.softeer.robocar.data.repository.route.RouteRemoteDataSource
import org.softeer.robocar.data.repository.route.RouteRemoteDataSourceImpl
import org.softeer.robocar.data.service.CarPool.CarPoolService
import org.softeer.robocar.data.service.RouteService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    @Singleton
    fun provideCarPoolRemoteDataSource(
        carPoolService: CarPoolService
    ): CarPoolRemoteDataSource {
        return CarPoolRemoteDataSourceImpl(carPoolService)
    }
    @Provides
    @Singleton
    fun provideRouteRemoteDataSource(
        routeService: RouteService
    ): RouteRemoteDataSource = RouteRemoteDataSourceImpl(routeService)
}