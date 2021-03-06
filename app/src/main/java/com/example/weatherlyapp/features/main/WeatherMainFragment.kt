package com.example.weatherlyapp.features.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherlyapp.R
import com.example.weatherlyapp.databinding.FragmentFirstBinding
import com.example.weatherlyapp.features.MainActivity
import com.example.weatherlyapp.features.MainActivityViewModel
import com.example.weatherlyapp.features.details.WeatherDetailsFragment
import com.example.weatherlyapp.utils.*
import com.example.weatherlyapp.utils.ResourceStates.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

class WeatherMainFragment : Fragment() {

    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivityViewModel?.executeGetCities()

        val citiesAdapter = WeatherAdapter()

        citiesAdapter.setOnClickCityListener(object : WeatherAdapter.OnClickCityListener{
            override fun onClickCity(cityName: String) {

                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out)
                    .add(R.id.base_rl, WeatherDetailsFragment.newInstance(cityName))
                    .addToBackStack(null)
                    .commit()
            }

        })

        _binding?.swipeRl?.setOnRefreshListener {
            mainActivityViewModel?.executeGetCities()
        }

        mainActivityViewModel?.cityList?.observe(requireActivity()){
            it?.run {
                when(this.resourceStates){
                    ResourceStates.SUCCESS ->{
                        _binding?.swipeRl?.isRefreshing = false
                        citiesAdapter.submitList(this.data)
                    }
                    LOADING ->{
                        _binding?.swipeRl?.isRefreshing = true
                    }
                    FAILED ->{
                        showToast(this.error!!)
                        _binding?.swipeRl?.isRefreshing = false

                    }
                }
            }
        }
        _binding?.citiesRv?.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = citiesAdapter
        }
//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_WeatherMainFragment_to_WeatherDetailsFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}