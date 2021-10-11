package com.example.weatherlyapp.features.details

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class WeatherDetailsPagerAdapter(private val views: List<View>) : PagerAdapter() {

    override fun getCount(): Int {
        return 2
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }


    override fun getPageTitle(position: Int): CharSequence? {
        // Return tab text label for position
        return arrayOf("Main", "Others")[position]
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        Log.d("instantiateItem", "")
        container.addView(views[position], position)
        return views[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    }
}