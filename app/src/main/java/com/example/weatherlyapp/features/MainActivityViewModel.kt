package com.example.weatherlyapp.features

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherlyapp.models.CityGroupResponse
import com.example.weatherlyapp.repository.WeatherRepository
import com.example.weatherlyapp.repository.WeatherRepositoryImpl
import com.example.weatherlyapp.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MainActivityViewModel @Inject constructor(val repository: WeatherRepositoryImpl) : ViewModel() {


    val _cityList = SingleLiveEvent<Resources<CityGroupResponse>>()
    val cityList : LiveData<Resources<CityGroupResponse>> = _cityList.mutate()

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

}