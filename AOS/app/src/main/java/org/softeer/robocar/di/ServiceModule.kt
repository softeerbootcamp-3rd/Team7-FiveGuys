package org.softeer.robocar.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.softeer.robocar.data.model.User
import org.softeer.robocar.data.service.CarPool.CarPoolService
import org.softeer.robocar.data.service.PlaceSearch.PlaceSearchService
import org.softeer.robocar.data.service.route.RouteService
import org.softeer.robocar.data.service.auth.AuthService
import org.softeer.robocar.data.service.user.UserService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    @Singleton
    fun provideCarPoolService(
        @BaseRetrofit retrofit: Retrofit
    ): CarPoolService = retrofit.create(CarPoolService::class.java)

    @Provides
    @Singleton
    fun providePlaceSearchService(
        @KakaoRetrofit retrofit: Retrofit
    ): PlaceSearchService = retrofit.create(PlaceSearchService::class.java)

    fun provideAuthService(
        retrofit: Retrofit
    ): AuthService = retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideUserService(
        retrofit: Retrofit
    ): UserService = retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideRouteService(
        retrofit: Retrofit
    ): RouteService = retrofit.create(RouteService::class.java)
    
}