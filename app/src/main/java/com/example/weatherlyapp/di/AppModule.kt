package com.example.weatherlyapp.di

import com.example.weatherlyapp.repository.WeatherRepository
import com.example.weatherlyapp.repository.WeatherRepositoryImpl
import com.example.weatherlyapp.repository.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideWeatherService(): WeatherService =
        Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)


}