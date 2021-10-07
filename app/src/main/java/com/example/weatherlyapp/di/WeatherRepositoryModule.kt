package com.example.weatherlyapp.di

import com.example.weatherlyapp.repository.WeatherRepository
import com.example.weatherlyapp.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class WeatherRepositoryModule {
    abstract fun bindWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository
}