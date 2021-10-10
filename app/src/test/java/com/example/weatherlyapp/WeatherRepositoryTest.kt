package com.example.weatherlyapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherlyapp.helpers.MockWebService
import com.example.weatherlyapp.repository.WeatherRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherRepositoryTest {


    @JvmField
    @Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @InjectMocks lateinit var webservice: MockWebService

    private val repository = mock(WeatherRepositoryImpl::class.java)

    @ExperimentalCoroutinesApi
    @Test
    fun testGetCities() = runBlockingTest {
        assertNotNull(repository)
        assertNotNull(webservice)
        val webserviceCity = webservice.getAllCities()
        `when`(repository.getCities()).thenReturn(webserviceCity)
        assertEquals(repository.getCities().getList()?.get(0)?.name, MockWebService.TEST_CITY_NAME)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetCityWeatherByName() = runBlockingTest{
        assertNotNull(repository)
        assertNotNull(webservice)
        val webServiceWeatherByName = webservice.getCityForecast(MockWebService.TEST_CITY_NAME)
        `when`(repository.getCityWeatherByName(MockWebService.TEST_CITY_NAME)).thenReturn(webServiceWeatherByName)
        assertEquals(repository.getCityWeatherByName(MockWebService.TEST_CITY_NAME).name, MockWebService.TEST_CITY_NAME)
    }

}