package com.example.weatherlyapp.repository.services

import com.example.weatherlyapp.repository.models.CityGroupResponse
import com.example.weatherlyapp.repository.models.WeatherResponse
import com.example.weatherlyapp.repository.services.API.CITY_IDS
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET(API.WEATHER_DATA +"/group")
    suspend fun getAllCities(@Query("id") groupIds: String = CITY_IDS) : CityGroupResponse


    @GET(API.WEATHER_DATA +"/weather")
    suspend fun getCityForecast(@Query("q") cityName : String) : WeatherResponse

}

object API{
    const val WEATHER_DATA = "data/2.5"
    const val BASE_URL ="https://api.openweathermap.org"
    const val CITY_IDS = "2332453,192950,360630,2352778,5128638,4736286,3665361,630336,932692,1642911,3204812,2335204,4905770,6183235,5178813,2650328"

}
