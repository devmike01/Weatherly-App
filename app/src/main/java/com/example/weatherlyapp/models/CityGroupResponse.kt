package com.example.weatherlyapp.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class CityGroupResponse {

    @SerializedName("cnt")
    @Expose
    private var cnt = 0

    @SerializedName("list")
    @Expose
    private var list: List<WeatherResponse>? = null

    fun getCnt(): Int {
        return cnt
    }

    fun setCnt(cnt: Int) {
        this.cnt = cnt
    }

    fun getList(): List<WeatherResponse?>? {
        return list
    }

    fun setList(list: List<WeatherResponse>?) {
        this.list = list
    }
}