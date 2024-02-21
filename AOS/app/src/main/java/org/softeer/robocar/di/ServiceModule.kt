package org.softeer.robocar.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.softeer.robocar.data.service.CarPool.CarPoolService
import org.softeer.robocar.data.service.PlaceSearch.PlaceSearchService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    @Singleton
    fun provideCarPoolService(
        retrofit: Retrofit
    ): CarPoolService = retrofit.create(CarPoolService::class.java)

    @Provides
    @Singleton
    fun providePlaceSearchService(
        retrofit: Retrofit
    ): PlaceSearchService = retrofit.create(PlaceSearchService::class.java)
}