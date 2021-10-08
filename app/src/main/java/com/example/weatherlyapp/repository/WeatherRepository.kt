package com.example.weatherlyapp.repository

import android.util.Log
import com.example.weatherlyapp.models.CityGroupResponse
import javax.inject.Inject

interface WeatherRepository {

    suspend fun getCities(): CityGroupResponse
}


class WeatherRepositoryImpl @Inject constructor(private val service: WeatherService)
    : WeatherRepository{

    companion object{
        const val CITY_IDS = "2332453,192950,360630,2352778,5128638,4736286,3665361,630336,932692,1642911,3204812,2335204,4905770,6183235, 2177263"
    }

    override suspend fun getCities(): CityGroupResponse {
        return service.getAllCities(CITY_IDS)
    }

//    private suspend fun cache() async{
//        service.getAllCities(CITY_IDS)
//    }

}