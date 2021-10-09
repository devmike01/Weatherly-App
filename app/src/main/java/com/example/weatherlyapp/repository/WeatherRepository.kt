package com.example.weatherlyapp.repository

import android.util.Log
import com.example.weatherlyapp.models.CityGroupResponse
import com.example.weatherlyapp.models.Weather
import com.example.weatherlyapp.models.WeatherResponse
import javax.inject.Inject

interface WeatherRepository {

    suspend fun getCities(): CityGroupResponse

    suspend fun getCityWeatherByName(cityName: String): WeatherResponse
}


class WeatherRepositoryImpl @Inject constructor(private val service: WeatherService)
    : WeatherRepository{

    override suspend fun getCities(): CityGroupResponse {
        return service.getAllCities()
    }

    override suspend fun getCityWeatherByName(cityName: String):WeatherResponse {
        Log.d("getCityWeatherByName", ""+  service.getCityForecast(cityName))
        return service.getCityForecast(cityName)
    }


}