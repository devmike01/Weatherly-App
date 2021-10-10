package com.example.weatherlyapp.utils

 interface Resources<T>
data class Success<T>(val data: T): Resources<T>
data class Failed<T>(val message: String, val data: T? = null): Resources<T>
sealed class Loading<T>(data: T? = null) : Resources<T>


enum class ResourceStates{
 LOADING, SUCCESS, FAILED
}


data class Resource<T>(val data: T?, val error: String?, val resourceStates: ResourceStates){

 companion object{
  fun <T> failed(error: String): Resource<T>{
   return Resource(error = error, data = null, resourceStates = ResourceStates.FAILED)
  }
  fun <T> success(data: T): Resource<T>{
   return Resource(error = null, data = data, resourceStates = ResourceStates.SUCCESS)
  }

  fun <T> loading(): Resource<T>{
   return Resource(error = null, data = null, resourceStates = ResourceStates.LOADING)
  }

 }
}