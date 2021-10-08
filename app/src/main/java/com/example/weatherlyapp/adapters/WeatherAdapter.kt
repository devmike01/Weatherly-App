package com.example.weatherlyapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherlyapp.R
import com.example.weatherlyapp.models.WeatherResponse

class WeatherAdapter : ListAdapter<WeatherResponse, WeatherAdapter.WeatherAdapterVH>(diffUtil) {


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

        fun bind(weatherResponse: WeatherResponse){
            itemView.apply{
                val weatherTv : TextView = findViewById(R.id.weather_tv)
                val temperatureTv : TextView = findViewById(R.id.temp_tv)
                val cityTv : TextView = findViewById(R.id.city_name_tv)
                val cityImageRl : RelativeLayout = findViewById(R.id.rl_bg)

                cityImageRl.setBackgroundResource(R.drawable.cairo_image)
                weatherTv.text = weatherResponse.name
                cityTv.text = weatherResponse.name
                val weatherSb = StringBuilder()
                "${weatherResponse.main?.temp}°".also { temperatureTv.text = it }
                weatherResponse.weather?.forEach {
                    weatherSb.append(it.main)
                }

                weatherTv.text = weatherSb.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherAdapterVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent, false)
        return WeatherAdapterVH(view)
    }

    override fun onBindViewHolder(holder: WeatherAdapterVH, position: Int) {
        holder.bind(getItem(position))
    }
}