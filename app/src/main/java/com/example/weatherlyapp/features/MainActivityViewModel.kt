package com.example.weatherlyapp.features

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherlyapp.models.CityGroupResponse
import com.example.weatherlyapp.models.WeatherResponse
import com.example.weatherlyapp.repository.WeatherRepository
import com.example.weatherlyapp.repository.WeatherRepositoryImpl
import com.example.weatherlyapp.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MainActivityViewModel @Inject constructor(val repository: WeatherRepositoryImpl) : ViewModel() {


    private val _cityList = SingleLiveEvent<Resources<CityGroupResponse>>()
    val cityList : LiveData<Resources<CityGroupResponse>> = _cityList.toLiveData()

    private val _cityWeatherDetails = MutableLiveData<Resources<WeatherResponse>>()
    val cityWeatherDetails : LiveData<Resources<WeatherResponse>> = _cityWeatherDetails

    val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }

    fun executeGetCities(){
        _cityList.value = Loading()
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            _cityList.value = Failed(exception.message ?: "Unknown error has occurred")
        }) {
            val cityGroupResponse = repository.getCities()
            _cityList.value = Success(cityGroupResponse)
        }
    }

    fun executeFetchWeatherByCityName(cityName: String){
        Log.d("getCityWeatherByName", "hello")
        _cityWeatherDetails.value = Loading()
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            _cityWeatherDetails.value = Failed(exception.message ?: "Unknown error has occurred")
        }){
            //Get details
            val weatherDetails = repository.getCityWeatherByName(cityName)
            _cityWeatherDetails.value = Success(weatherDetails)
        }
    }

}