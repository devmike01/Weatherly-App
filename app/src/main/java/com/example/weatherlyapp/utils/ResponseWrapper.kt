package com.example.weatherlyapp.utils

import androidx.lifecycle.LiveData

data class ResponseWrapper<T>(val isSuccess: Boolean, val data: T?, val error: String?)