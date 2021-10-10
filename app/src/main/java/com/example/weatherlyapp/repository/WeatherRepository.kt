package com.example.weatherlyapp.repository

import com.example.weatherlyapp.models.CityGroupResponse
import com.example.weatherlyapp.models.WeatherResponse
import javax.inject.Inject

interface WeatherRepository {

    suspend fun getCities(): CityGroupResponse

    suspend fun getCityWeatherByName(cityName: String): WeatherResponse
}


open class WeatherRepositoryImpl @Inject constructor(private val service: WeatherService)
    : WeatherRepository{

    override suspend fun getCities(): CityGroupResponse {
        return service.getAllCities()
    }

    override suspend fun getCityWeatherByName(cityName: String):WeatherResponse {
        return service.getCityForecast(cityName)
    }


}