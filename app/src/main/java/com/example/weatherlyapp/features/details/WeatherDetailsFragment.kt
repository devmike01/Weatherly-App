package com.example.weatherlyapp.features.details

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.weatherlyapp.R
import com.example.weatherlyapp.databinding.FragmentSecondBinding
import com.example.weatherlyapp.features.MainActivity
import com.example.weatherlyapp.features.MainActivityViewModel
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


    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(ARG_CITY_NAME)?.apply {
            mainActivityViewModel.executeFetchWeatherByCityName(this);
        }


        (activity as MainActivity).run{
            setSupportActionBar(_binding?.toolbar)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false);

            _binding?.toolbar?.setNavigationOnClickListener { requireActivity().supportFragmentManager.popBackStack()}
        }

        mainActivityViewModel.cityWeatherDetails.observe(viewLifecycleOwner){
            when(it){
                is Success ->{
                    _binding?.progressBar?.hide()
                    it.data.run {
                        _binding?.weatherTv?.text = this.weather?.get(0)?.main
                        _binding?.cityStateTv?.text = "${ this.name},${ this.sys?.country}"
                        val temperature =  this.main?.temp?.toCelsiusDouble()
                        "min ${this.main?.tempMin ?: 0}, ${this.main?.tempMax ?: 0} max".also { _binding?.minMaxTv?.text = it }
                        setTemperature(temperature ?: 0.0, _binding?.tempTv )
                        _binding?.baseTv?.text = this.base.let { be ->"${be?.get(0)?.uppercase()}${be?.substring(1, be.length)}" }
                        _binding?.pressureTv?.text = this.main?.pressure.toString().plus(" hPa")
                        _binding?.humidityTv?.text = this.main?.humidity.toString().plus("%")
                        _binding?.descriptionTv?.text = this.weather?.get(0)?.description?.let {descrp -> "${descrp[0].uppercase()}${descrp.substring(1, descrp.length)}" }
                        if(_binding?.weatherIcIv != null){
                            Glide.with(requireActivity()).load(this.weather?.get(0)?.iconUrl()).into(_binding?.weatherIcIv!!)
                        }
                        animateDetailsViews()
                    }
                }
                is Failed ->{
                    Log.d("getCityWeatherByName", "hello ${it.message}")
                    showToast(it.message)
                    _binding?.progressBar?.hide()
                }
                is Loading ->{
                    _binding?.progressBar?.show()
                }
            }
        }

    }


    private fun setTemperature(value: Double, textView: TextView?) {
        val animator = ValueAnimator.ofFloat(0f, value.toFloat())
        animator.duration = 700
        animator.addUpdateListener { animation ->
            textView?.text = requireActivity().resources.getString(R.string.degree_celsius,
                animation.animatedValue.toString())
        }
        animator.start()
    }

    private fun animateDetailsViews(){
        ObjectAnimator.ofFloat(_binding?.otherDetailsLl, "translationY",
            200f, 0f).apply {
            duration = 300
            _binding?.otherDetailsLl?.visibility = View.VISIBLE
            start()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}