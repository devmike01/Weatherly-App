package com.example.weatherlyapp.repository

import com.example.weatherlyapp.models.CityResponse
import javax.inject.Inject

interface WeatherRepository {
}


class WeatherRepositoryImpl @Inject constructor(service: WeatherService) : WeatherRepository{

}