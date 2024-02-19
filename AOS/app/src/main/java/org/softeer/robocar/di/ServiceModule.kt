package org.softeer.robocar.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.softeer.robocar.data.service.CarPool.CarPoolService
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

}