package org.softeer.robocar.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.softeer.robocar.data.repository.CarPool.CarPoolLocalDataSource
import org.softeer.robocar.data.repository.CarPool.CarPoolRemoteDataSource
import org.softeer.robocar.data.repository.CarPool.CarPoolRepository
import org.softeer.robocar.data.repository.CarPool.CarPoolRepositoryImpl
import org.softeer.robocar.data.repository.PlaceSearch.PlaceSearchRemoteDataSource
import org.softeer.robocar.data.repository.PlaceSearch.PlaceSearchRepository
import org.softeer.robocar.data.repository.PlaceSearch.PlaceSearchRepositoryImpl
import org.softeer.robocar.data.repository.auth.AuthLocalDataSource
import org.softeer.robocar.data.repository.auth.AuthRemoteDataSource
import org.softeer.robocar.data.repository.auth.AuthRepository
import org.softeer.robocar.data.repository.auth.AuthRepositoryImpl
import org.softeer.robocar.data.repository.route.RouteRemoteDataSource
import org.softeer.robocar.data.repository.route.RouteRepository
import org.softeer.robocar.data.repository.route.RouteRepositoryImpl
import org.softeer.robocar.data.repository.user.UserRemoteDataSource
import org.softeer.robocar.data.repository.user.UserRepository
import org.softeer.robocar.data.repository.user.UserRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideCarPoolRepository(
        carPoolRemoteDataSource: CarPoolRemoteDataSource,
        authLocalDataSource: AuthLocalDataSource,
        carPoolLocalDataSource: CarPoolLocalDataSource
    ): CarPoolRepository {
        return CarPoolRepositoryImpl(
            carPoolRemoteDataSource,
            authLocalDataSource,
            carPoolLocalDataSource
            )
    }

    @Provides
    @Singleton
    fun providePlaceSearchRepository(
        placeSearchRemoteDataSource: PlaceSearchRemoteDataSource
    ): PlaceSearchRepository {
        return PlaceSearchRepositoryImpl(placeSearchRemoteDataSource)
    }
    @Provides
    @Singleton
    fun provideAuthRepository(
        authRemoteDataSource: AuthRemoteDataSource,
        authLocalDataSource: AuthLocalDataSource,
    ): AuthRepository {
        return AuthRepositoryImpl(
            authRemoteDataSource,
            authLocalDataSource
        )
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        userRemoteDataSource: UserRemoteDataSource
    ): UserRepository {
        return UserRepositoryImpl(
            userRemoteDataSource
        )
    }

    @Provides
    @Singleton
    fun provideRouteRepository(
        routeRemoteDataSource: RouteRemoteDataSource,
        authLocalDataSource: AuthLocalDataSource
    ): RouteRepository {
        return RouteRepositoryImpl(
            routeRemoteDataSource,
            authLocalDataSource
        )
    }
}