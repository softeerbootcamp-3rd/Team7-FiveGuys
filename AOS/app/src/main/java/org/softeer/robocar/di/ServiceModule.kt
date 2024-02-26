package org.softeer.robocar.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.softeer.robocar.data.service.CarPool.CarPoolService
import org.softeer.robocar.data.service.PlaceSearch.PlaceSearchService
import org.softeer.robocar.data.service.addresssearch.AddressSearchService
import org.softeer.robocar.data.service.route.RouteService
import org.softeer.robocar.data.service.auth.AuthService
import org.softeer.robocar.data.service.operation.OnboardService
import org.softeer.robocar.data.service.route.RouteSoloService
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

    @Provides
    @Singleton
    fun provideAddressSearchService(
        @KakaoRetrofit retrofit: Retrofit
    ): AddressSearchService = retrofit.create(AddressSearchService::class.java)

    @Singleton
    @Provides
    fun provideAuthService(
        @BaseRetrofit retrofit: Retrofit
    ): AuthService = retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideUserService(
        @BaseRetrofit retrofit: Retrofit
    ): UserService = retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideOnboardService(
        @BaseRetrofit retrofit: Retrofit
    ): OnboardService = retrofit.create(OnboardService::class.java)

    @Provides
    @Singleton
    fun provideRouteService(
        @BaseRetrofit retrofit: Retrofit
    ): RouteService = retrofit.create(RouteService::class.java)

    @Provides
    @Singleton
    fun provideSoloRouteService(
        @BaseRetrofit retrofit: Retrofit
    ): RouteSoloService = retrofit.create(RouteSoloService::class.java)
    
}