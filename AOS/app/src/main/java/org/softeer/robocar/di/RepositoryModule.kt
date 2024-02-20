package org.softeer.robocar.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.softeer.robocar.data.repository.CarPool.CarPoolRemoteDataSource
import org.softeer.robocar.data.repository.CarPool.CarPoolRepository
import org.softeer.robocar.data.repository.CarPool.CarPoolRepositoryImpl
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
}