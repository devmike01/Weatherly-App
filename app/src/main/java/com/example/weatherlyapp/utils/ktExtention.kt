package com.example.weatherlyapp.utils

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment


fun ProgressBar.hide(){
    visibility = View.GONE
}

fun ProgressBar.show(){
    visibility = View.VISIBLE
}

fun Fragment.showToast( msg: String){
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}