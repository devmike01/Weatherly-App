package com.example.weatherlyapp.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.weatherlyapp.models.Weather
import com.example.weatherlyapp.models.WeatherResponse
import com.example.weatherlyapp.repository.API


fun ProgressBar.hide(){
    visibility = View.GONE
}

fun ProgressBar.show(){
    visibility = View.VISIBLE
}

fun Weather.iconUrl(): String?{
    return icon?.let{ "${API.BASE_URL}/img/w/${it}.png" }
}

fun Fragment.showToast( msg: String){
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}

fun Double.toCelsiusDouble(): Double{
    return this- 273.15
}

fun Any.toCelsius(): String{
    Log.d("toCelsius", ""+this.toString().toDouble())
    if (this is Number){
        return "${this.toString().toDouble()}°C"
    }
    return "0°C"
}