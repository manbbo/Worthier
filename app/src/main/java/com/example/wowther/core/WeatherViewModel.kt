package com.example.wowther.core

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wowther.core.data.WeatherData
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val service = WeatherService.api

    val locationInfos: MutableLiveData<Map<String, WeatherData>> = MutableLiveData(emptyMap())
    val hasError: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getWeatherByLocation(lat: String, lon: String) {
        viewModelScope.launch {
            try {
                val weather = service.getWeatherByLatLon(lat, lon)
                addWeatherToMap(weather)
                hasError.postValue(false)
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    fun getWeatherByName(locationName: String) {
        viewModelScope.launch {
            try {
                val weather = service.getWeatherByLocation(locationName = locationName)
                addWeatherToMap(weather)
                hasError.postValue(false)
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    private fun addWeatherToMap(weatherData: WeatherData) {
        val updatedMap = (locationInfos.value ?: emptyMap()).plus(weatherData.name to weatherData)
        locationInfos.postValue(updatedMap)
    }

    fun initializeWeatherData() {
        viewModelScope.launch {
            try {
                Log.d("WeatherViewModel", "Initializing weather data...")

                val locations = listOf(
                    "Montevideo",
                    "Londres",
                    "San Pablo",
                    "Buenos Aires",
                    "Munich"
                )

                val weatherMap = locations.associateWith { location ->
                    Log.d("WeatherViewModel", "Fetching weather data for location: $location")
                    service.getWeatherByLocation(locationName = location)
                }

                Log.d("WeatherViewModel", "Weather data initialized: $weatherMap")
                locationInfos.postValue(weatherMap)

                hasError.postValue(false)
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    private fun handleException(exception: Exception) {
        hasError.postValue(true)
        Log.e("WeatherViewModel", "Error occurred: ${exception.message}", exception)
    }
}