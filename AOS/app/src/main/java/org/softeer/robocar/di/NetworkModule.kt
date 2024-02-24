package org.softeer.robocar.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.softeer.robocar.BuildConfig
import org.softeer.robocar.utils.interceptor.ParseResponseDataInterceptor
import org.softeer.robocar.utils.interceptor.ResponseLoggingInterceptor
import org.softeer.robocar.utils.interceptor.TokenAuthenticator
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class KakaoRetrofit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(ParseResponseDataInterceptor())
            .addInterceptor(ResponseLoggingInterceptor())
            .authenticator(TokenAuthenticator())
            .build()
    }

    @Provides
    @Singleton
    @BaseRetrofit
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory(("application/json").toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    @KakaoRetrofit
    fun provideRetrofitKakao(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com/")
            .addConverterFactory(Json.asConverterFactory(("application/json").toMediaType()))
            .build()
    }
}