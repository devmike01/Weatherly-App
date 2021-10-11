package com.example.weatherlyapp.features

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherlyapp.repository.models.CityGroupResponse
import com.example.weatherlyapp.repository.models.WeatherResponse
import com.example.weatherlyapp.repository.WeatherRepository
import com.example.weatherlyapp.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
open class MainActivityViewModel @Inject constructor(val repository: WeatherRepository) : ViewModel() {


    private val _cityList = SingleLiveEvent<Resource<List<WeatherResponse>>>()
    val cityList : LiveData<Resource<List<WeatherResponse>>> = _cityList.toLiveData()

    private val _cityWeatherDetails = MutableLiveData<Resource<WeatherResponse>>()
    val cityWeatherDetails : LiveData<Resource<WeatherResponse>> = _cityWeatherDetails


    fun executeGetCities(){
        _cityList.value = Resource.loading()
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            _cityList.value = Resource.failed(exception.message ?: "Unknown error has occurred")
        }) {
            val cityGroupResponse = repository.getCities()
            cityGroupResponse.run {
                _cityList.value = Resource.success(this.getList())
            }
        }
    }

    fun executeFetchWeatherByCityName(cityName: String){
        _cityWeatherDetails.value = Resource.loading()
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            _cityWeatherDetails.value = Resource.failed(exception.message ?: "Unknown error has occurred")
        }){
            //Get details
            val weatherDetails = repository.getCityWeatherByName(cityName)
            weatherDetails?.run {
                _cityWeatherDetails.value = Resource.success(this)
            }
        }
    }

}