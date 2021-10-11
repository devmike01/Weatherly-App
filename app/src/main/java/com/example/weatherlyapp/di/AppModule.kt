package com.example.weatherlyapp.di

import com.example.weatherlyapp.repository.services.API
import com.example.weatherlyapp.repository.services.ServerInterceptor
import com.example.weatherlyapp.repository.services.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherService(errorOkHttpClient: OkHttpClient): WeatherService =
        Retrofit.Builder()
            .baseUrl(API.BASE_URL)
            .client(errorOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)

    @Provides
    fun provideServerErrorInterceptor(serverInterceptor: ServerInterceptor): OkHttpClient{

        return OkHttpClient.Builder()
            .addInterceptor(serverInterceptor)
            .build()
    }

}