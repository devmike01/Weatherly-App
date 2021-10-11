package com.example.weatherlyapp.helpers

import com.example.weatherlyapp.repository.models.CityGroupResponse
import com.example.weatherlyapp.repository.models.WeatherResponse
import com.example.weatherlyapp.repository.WeatherService
import kotlinx.coroutines.delay

open class MockWebService : WeatherService {

    companion object{
        const val TEST_CITY_NAME ="Cairo"
    }

    override suspend fun getAllCities(groupIds: String, units: String): CityGroupResponse {
        val cityGroupResponse = CityGroupResponse()
        cityGroupResponse.setCnt(2)
        val weatherResponse = WeatherResponse()
        val list = ArrayList<WeatherResponse>()
        weatherResponse.name = TEST_CITY_NAME
        list.add(weatherResponse)
        cityGroupResponse.setList(list)
       // delay(1000)
       return cityGroupResponse
    }

    override suspend fun getCityForecast(cityName: String): WeatherResponse {
        val weatherResponse = WeatherResponse()
        weatherResponse.name = cityName
        weatherResponse.timezone = 10000
        delay(1000)
        return weatherResponse
    }
}