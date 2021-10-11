package com.example.weatherlyapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.weatherlyapp.features.MainActivityViewModel
import com.example.weatherlyapp.repository.WeatherRepositoryImpl
import com.example.weatherlyapp.repository.services.WeatherService
import com.example.weatherlyapp.utils.*

import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MainActivityViewModelTest {

    @JvmField
    @Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mainThreadSurrogate: ExecutorCoroutineDispatcher

    @ExperimentalCoroutinesApi
    private var mainActivityViewModel : MainActivityViewModel? =null

    @Mock
    lateinit var cityObserver : Observer<Resource<*>>

    private val webservice = mock(WeatherService::class.java)

    private val repository = mock(WeatherRepositoryImpl::class.java)

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @Before
    fun init(){
        mainThreadSurrogate = newSingleThreadContext("UI thread")
        mainActivityViewModel = MainActivityViewModel(repository)
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun textExecuteGetCities() = runBlockingTest {
        mainActivityViewModel?.executeGetCities()
        val cities = webservice.getAllCities()
        lenient().`when`(repository.getCities()).thenReturn(cities)
        verifyNoInteractions(cityObserver)
        try {
            mainActivityViewModel?.cityList?.observeForever(cityObserver)
            verify(cityObserver).onChanged(any(Resource::class.java))
        }finally {
            mainActivityViewModel?.cityList?.removeObserver(cityObserver)
        }
    }



    @ExperimentalCoroutinesApi
    @Test
    fun textExecuteGetCityForecast() = runBlockingTest {
        mainActivityViewModel?.executeFetchWeatherByCityName("")
        val cities = webservice.getCityForecast(anyString())
        lenient().`when`(repository.getCityWeatherByName(anyString())).thenReturn(cities)
        verifyNoInteractions(cityObserver)
        try {
            mainActivityViewModel?.cityWeatherDetails?.observeForever(cityObserver)
            verify(cityObserver).onChanged(any(Resource::class.java))
        }finally {
            mainActivityViewModel?.cityWeatherDetails?.removeObserver(cityObserver)
        }
    }

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @After
    fun tearDown(){

        Dispatchers.resetMain()
        mainThreadSurrogate.close()
        mainActivityViewModel = null

    }

}
