package com.example.weatherlyapp.features.details

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.weatherlyapp.R
import com.example.weatherlyapp.databinding.FragmentSecondBinding
import com.example.weatherlyapp.features.MainActivity
import com.example.weatherlyapp.features.MainActivityViewModel
import com.example.weatherlyapp.repository.models.WeatherResponse
import com.example.weatherlyapp.utils.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

class WeatherDetailsFragment : Fragment() {


    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()


    companion object{

        private const val ARG_CITY_NAME = "ARG_CITY_NAME"

        fun newInstance(cityName: String): WeatherDetailsFragment {
            val bundle = Bundle()
            bundle.putString(ARG_CITY_NAME, cityName)
            val weatherDetailsFragment = WeatherDetailsFragment()
            weatherDetailsFragment.arguments = bundle
            return weatherDetailsFragment
        }
    }


    val viewList = arrayListOf<View>()
    private var _binding: FragmentSecondBinding? = null

    private lateinit var pagerAdapter : WeatherDetailsPagerAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(ARG_CITY_NAME)?.apply {
            mainActivityViewModel.executeFetchWeatherByCityName(this);


            _binding?.swipeRl?.setOnRefreshListener {
                mainActivityViewModel.executeFetchWeatherByCityName(this)
            }
        }


        (activity as MainActivity).run{
            setSupportActionBar(_binding?.toolbar)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false);

            _binding?.toolbar?.setNavigationOnClickListener { requireActivity().supportFragmentManager.popBackStack()}
        }

        mainActivityViewModel.cityWeatherDetails.observe(viewLifecycleOwner){
            when(it.resourceStates){
                ResourceStates.SUCCESS ->{

                    _binding?.swipeRl?.isRefreshing =false
                    it.data?.run {
                        _binding?.weatherTv?.text = this.weather?.get(0)?.main
                        _binding?.cityStateTv?.text = "${ this.name},${ this.sys?.country}"
                        val temperature =  this.main?.temp
                        loadImages()[this.id]?.run {  _binding?.rlBg?.setCityImage(this ) }

                        "min ${this.main?.tempMin ?: 0}, ${this.main?.tempMax ?: 0} max".also { _binding?.minMaxTv?.text = it }
                        setTemperature(temperature ?: 0.0, _binding?.tempTv )
                        viewPager(this)
                        animateDetailsViews()
                    }
                }
                ResourceStates.FAILED ->{

                    _binding?.swipeRl?.isRefreshing =false
                    showToast(it.error!!)
                }
                ResourceStates.LOADING ->{
                    _binding?.swipeRl?.isRefreshing =true
                }
            }
        }



        pagerAdapter = WeatherDetailsPagerAdapter(viewList)
        _binding?.viewpager?.adapter = pagerAdapter
        _binding?.tablayout?.setupWithViewPager(_binding?.viewpager)

    }


    private fun viewPager(weatherResponse: WeatherResponse){
        Log.d("WeatherResponse", "${weatherResponse.name}")

        val otherDetailView = LayoutInflater.from(activity).inflate(R.layout.other_details_layout, null)

        val  mainDetailView = LayoutInflater.from(activity).inflate(R.layout.main_details_layout, null)
        mainDetailView?.run {
            findViewById<TextView>(R.id.base_tv)?.text = weatherResponse.base.let { be ->"${be?.get(0)?.uppercase()}${be?.substring(1, be.length)}" }
            findViewById<TextView>(R.id.pressure_tv)?.text = weatherResponse.main?.pressure.toString().plus(" hPa")
            findViewById<TextView>(R.id.humidity_tv)?.text = weatherResponse.main?.humidity.toString().plus("%")
            findViewById<TextView>(R.id.description_tv)?.text = weatherResponse.weather?.get(0)?.description?.let {descrp -> "${descrp[0].uppercase()}${descrp.substring(1, descrp.length)}" }
            val weatherIcIv = findViewById<ImageView>(R.id.weather_ic_iv)
            if(weatherIcIv != null){
                Glide.with(requireActivity()).load(weatherResponse.weather?.get(0)?.iconUrl()).into(weatherIcIv)
            }

            Log.d("WeatherResponse", "${weatherResponse.name}")
        }

        otherDetailView?.run {
            findViewById<TextView>(R.id.feel_like_tv)?.text = activity?.getString(R.string.degree_celsius, "${weatherResponse.main?.feelsLike ?:0 }")
            findViewById<TextView>(R.id.wind_speed_tv)?.text = "${weatherResponse.wind?.speed.let { it ?:0 }}"
            findViewById<TextView>(R.id.visibility_tv)?.text = weatherResponse.visibility.toString()
            findViewById<TextView>(R.id.lat_tv)?.text = "${weatherResponse.coord?.lat}"
            findViewById<TextView>(R.id.lng_tv)?.text = "${weatherResponse.coord?.lon}"
            findViewById<TextView>(R.id.wind_speed_tv)?.text = "${weatherResponse.wind?.deg}°"
        }

        viewList.add(mainDetailView)
        viewList.add(otherDetailView)
        pagerAdapter.notifyDataSetChanged()
    }

    private fun setTemperature(value: Double, textView: TextView?) {
        activity?.run {
            val animator = ValueAnimator.ofFloat(0f, value.toFloat())
            animator.duration = 700
            animator.addUpdateListener { animation ->
                textView?.text = this.resources.getString(R.string.degree_celsius,
                    animation.animatedValue.toString())
            }
            animator.start()
        }
    }

    private fun animateDetailsViews(){
        ObjectAnimator.ofFloat(_binding?.otherDetailsCv, "translationY",
            200f, 0f).apply {
            duration = 100
            _binding?.otherDetailsCv?.visibility = View.VISIBLE
            start()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}