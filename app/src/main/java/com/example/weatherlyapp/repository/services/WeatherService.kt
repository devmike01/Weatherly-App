package com.example.weatherlyapp.repository

import com.example.weatherlyapp.models.CityGroupResponse
import com.example.weatherlyapp.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET(API.WEATHER_DATA +"/group")
    suspend fun getAllCities(@Query("id") groupIds: String,
                             @Query("units") units: String = "metric") : CityGroupResponse

    suspend fun getCityForecast(@Path("cityId") cityId : String) : WeatherResponse

}

object API{
    const val WEATHER_DATA = "data/2.5"
}

/*

http://api.openweathermap.org/data/2.5/group?id=2332453,703448,2643743&units=metric&appid=6e76055f1d172fe00a7aa9edd1f0ebf5


 */