package org.softeer.robocar.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.softeer.robocar.data.repository.CarPool.CarPoolRemoteDataSource
import org.softeer.robocar.data.repository.CarPool.CarPoolRemoteDataSourceImpl
import org.softeer.robocar.data.repository.PlaceSearch.PlaceSearchRemoteDataSource
import org.softeer.robocar.data.repository.PlaceSearch.PlaceSearchRemoteDataSourceImpl
import org.softeer.robocar.data.service.PlaceSearch.PlaceSearchService
import org.softeer.robocar.data.repository.auth.AuthLocalDataSource
import org.softeer.robocar.data.repository.auth.AuthLocalDataSourceImpl
import org.softeer.robocar.data.repository.auth.AuthRemoteDataSource
import org.softeer.robocar.data.repository.auth.AuthRemoteDataSourceImpl
import org.softeer.robocar.data.repository.route.RouteRemoteDataSource
import org.softeer.robocar.data.repository.route.RouteRemoteDataSourceImpl
import org.softeer.robocar.data.repository.user.UserRemoteDataSource
import org.softeer.robocar.data.repository.user.UserRemoteDataSourceImpl
import org.softeer.robocar.data.service.CarPool.CarPoolService
import org.softeer.robocar.data.service.route.RouteService
import org.softeer.robocar.data.service.auth.AuthService
import org.softeer.robocar.data.service.user.UserService
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

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(
        userService: UserService
    ): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(userService)
    }

    @Provides
    @Singleton
    fun provideRouteRemoteDataSource(
        routeService: RouteService
    ): RouteRemoteDataSource {
        return RouteRemoteDataSourceImpl(routeService)
    }
}