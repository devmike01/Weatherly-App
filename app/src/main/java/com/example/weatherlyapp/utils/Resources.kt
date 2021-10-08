package com.example.weatherlyapp.utils

 interface Resources<T>
data class Success<T>(val data: T): Resources<T>
data class Failed<T>(val message: String, val data: T? = null): Resources<T>
class Loading<T>(data: T? = null) : Resources<T>