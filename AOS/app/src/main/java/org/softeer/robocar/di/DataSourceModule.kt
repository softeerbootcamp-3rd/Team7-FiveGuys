package org.softeer.robocar.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.softeer.robocar.data.repository.CarPool.CarPoolRemoteDataSource
import org.softeer.robocar.data.repository.CarPool.CarPoolRemoteDataSourceImpl
import org.softeer.robocar.data.repository.auth.AuthLocalDataSource
import org.softeer.robocar.data.repository.auth.AuthLocalDataSourceImpl
import org.softeer.robocar.data.repository.auth.AuthRemoteDataSource
import org.softeer.robocar.data.repository.auth.AuthRemoteDataSourceImpl
import org.softeer.robocar.data.service.CarPool.CarPoolService
import org.softeer.robocar.data.service.auth.AuthService
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
    fun provideAuthRemoteDataSource(
        authService: AuthService
    ): AuthRemoteDataSource {
        return AuthRemoteDataSourceImpl(authService)
    }

    @Provides
    @Singleton
    fun provideAuthLocalDataSource(
        @ApplicationContext context: Context
    ): AuthLocalDataSource {
        return AuthLocalDataSourceImpl(context)
    }
}