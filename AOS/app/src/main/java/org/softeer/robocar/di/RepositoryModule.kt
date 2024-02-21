package org.softeer.robocar.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.softeer.robocar.data.repository.CarPool.CarPoolRemoteDataSource
import org.softeer.robocar.data.repository.CarPool.CarPoolRepository
import org.softeer.robocar.data.repository.CarPool.CarPoolRepositoryImpl
import org.softeer.robocar.data.repository.PlaceSearch.PlaceSearchRemoteDataSource
import org.softeer.robocar.data.repository.PlaceSearch.PlaceSearchRepository
import org.softeer.robocar.data.repository.PlaceSearch.PlaceSearchRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideCarPoolRepository(
        carPoolRemoteDataSource: CarPoolRemoteDataSource
    ): CarPoolRepository {
        return CarPoolRepositoryImpl(carPoolRemoteDataSource)
    }

    @Provides
    @Singleton
    fun providePlaceSearchRepository(
        placeSearchRemoteDataSource: PlaceSearchRemoteDataSource
    ): PlaceSearchRepository {
        return PlaceSearchRepositoryImpl(placeSearchRemoteDataSource)
    }
}