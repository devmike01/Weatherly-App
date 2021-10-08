package com.example.weatherlyapp.di

import androidx.lifecycle.ViewModelProvider
import com.example.weatherlyapp.features.MainActivityViewModel
import com.example.weatherlyapp.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
object MainActivityViewModelModule {

//    @Provides
//    fun bindMainActivityViewModel(repository: WeatherRepository): MainActivityViewModel{
//        return ViewModelProvider
//    }


}