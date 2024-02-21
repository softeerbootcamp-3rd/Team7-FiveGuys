package org.softeer.robocar.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.softeer.robocar.data.repository.CarPool.CarPoolRemoteDataSource
import org.softeer.robocar.data.repository.CarPool.CarPoolRemoteDataSourceImpl
import org.softeer.robocar.data.repository.PlaceSearch.PlaceSearchRemoteDataSource
import org.softeer.robocar.data.repository.PlaceSearch.PlaceSearchRemoteDataSourceImpl
import org.softeer.robocar.data.service.CarPool.CarPoolService
import org.softeer.robocar.data.service.PlaceSearch.PlaceSearchService
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
    fun providePlaceSearchRemoteDataSource(
        placeSearchService: PlaceSearchService
    ): PlaceSearchRemoteDataSource {
        return PlaceSearchRemoteDataSourceImpl(placeSearchService)
    }
}