package com.example.weatherlyapp.utils

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.weatherlyapp.R
import com.example.weatherlyapp.features.main.WeatherAdapter
import com.example.weatherlyapp.repository.models.CityGroupResponse
import com.example.weatherlyapp.repository.models.Weather
import com.example.weatherlyapp.repository.services.API
import android.graphics.drawable.Drawable

import android.widget.RelativeLayout
import androidx.annotation.Nullable

import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.Glide
import com.bumptech.glide.request.transition.Transition


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


fun Any.toCelsius(): String{
    Log.d("toCelsius", ""+this.toString().toDouble())
    if (this is Number){
        return "${this.toString().toDouble()}°C"
    }
    return "0°C"
}

fun WeatherAdapter.loadImages(): Map<Int, Int>{
    return mapOf(
        2332453 to R.drawable.lagos,
        192950 to R.drawable.kenya_lesotho,
        360630 to R.drawable.cairo_image,
        2352778 to R.drawable.abuja,
        5128638 to R.drawable.new_york,
        4736286 to R.drawable.texas,
        3665361 to R.drawable.amazonas, //To be examined later
        630336 to R.drawable.belarus,
        1642911 to R.drawable.jjarkata,
        932692 to R.drawable.kenya_lesotho,
        2335204 to R.drawable.kano,
        3204812 to R.drawable.ankara,
        6183235 to R.drawable.winniepeg,
        4905770 to R.drawable.peru,
        2650328 to R.drawable.westham,
        5178813 to R.drawable.bagdad,
        // 932692
    )
}

fun RelativeLayout.setCityImage(image: Int){
    Glide.with(this@setCityImage.context)
        .load(image)
        .into(object : CustomTarget<Drawable?>() {

            override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
            override fun onResourceReady(
                resource: Drawable,
                transition: Transition<in Drawable?>?
            ) {
                this@setCityImage.background = resource
            }
        })
}