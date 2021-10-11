package com.example.weatherlyapp.features.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherlyapp.R
import com.example.weatherlyapp.repository.models.WeatherResponse
import com.example.weatherlyapp.utils.iconUrl
import com.example.weatherlyapp.utils.loadImages
import com.example.weatherlyapp.utils.setCityImage

class WeatherAdapter : ListAdapter<WeatherResponse, WeatherAdapter.WeatherAdapterVH>(diffUtil) {

    val cityImages = loadImages()

    interface OnClickCityListener{
        fun onClickCity(cityName: String)
    }

    private var onClickCityListener : OnClickCityListener? =null

    public fun setOnClickCityListener(onClickCityListener: OnClickCityListener){
        this.onClickCityListener = onClickCityListener
    }

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<WeatherResponse>(){
            override fun areItemsTheSame(
                oldItem: WeatherResponse,
                newItem: WeatherResponse
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: WeatherResponse,
                newItem: WeatherResponse
            ): Boolean = oldItem == newItem

        }
    }

    inner class WeatherAdapterVH(private val itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(weatherResponse: WeatherResponse, cityImages: Map<Int, Int>){
            itemView.apply{
                val weatherTv : TextView = findViewById(R.id.weather_tv)
                val temperatureTv : TextView = findViewById(R.id.temp_tv)
                val cityTv : TextView = findViewById(R.id.city_name_tv)
                val weatherIcon : ImageView = findViewById(R.id.weather_ic_im)
                val cityImageRl : RelativeLayout = findViewById(R.id.rl_bg)

                if (cityImages[weatherResponse.id] != null) {
                    cityImageRl.setCityImage(cityImages[weatherResponse.id]!!)
                }else{
                    Glide.with(context).clear(cityImageRl);
                    // remove the placeholder (optional); read comments below
                    cityImageRl.background = (null);
                    Log.d("WeatherAdapterVH", "WHCH ${weatherResponse.id}")
                }

                weatherTv.text = weatherResponse.name
                cityTv.text = weatherResponse.name
                temperatureTv.text = context.let { resources.getString(R.string.degree_celsius, weatherResponse.main?.temp.toString()) }
                Glide.with(context).load(weatherResponse.weather?.get(0)?.iconUrl()).into(weatherIcon)

                weatherTv.text = weatherResponse.weather?.get(0)?.main

                setOnClickListener {
                    val cityName = weatherResponse.name.let { it  ?: "NA" }
                    onClickCityListener?.onClickCity(cityName)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherAdapterVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent, false)
        return WeatherAdapterVH(view)
    }

    override fun onBindViewHolder(holder: WeatherAdapterVH, position: Int) {
        holder.bind(getItem(position), cityImages = cityImages)
    }
}