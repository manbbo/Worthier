package com.example.wowther.core

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wowther.core.data.WeatherData
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val service = WeatherService.api

    val initialLocationInfos: MutableLiveData<Map<String, WeatherData>> = MutableLiveData(emptyMap())
    val hasError: MutableLiveData<Boolean> = MutableLiveData(false)
    val locationInfos: MutableLiveData<WeatherData> = MutableLiveData()

    fun getWeatherByLocation(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                locationInfos.postValue(service.getWeatherByLatLon(lat, lon))

                hasError.postValue(false)
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    fun getWeatherByName(locationName: String) {
        viewModelScope.launch {
            try {
                locationInfos.postValue(service.getWeatherByLocation(locationName = locationName))

                hasError.postValue(false)
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    fun initializeWeatherData(lat: Double, lon: Double) {
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

                val weatherMap = mutableMapOf<String, WeatherData>()

                Log.d("WeatherViewModel", "Fetching weather data for coordinates: $lat, $lon")
                val yourLocation = service.getWeatherByLatLon(lat, lon)
                weatherMap.put(yourLocation.name, yourLocation)

                locations.associateWith { location ->
                    Log.d("WeatherViewModel", "Fetching weather data for location: $location")
                    val weather = service.getWeatherByLocation(locationName = location)

                    weatherMap.put(weather.name, weather)
                }

                Log.d("WeatherViewModel", "Weather data initialized: $weatherMap")
                initialLocationInfos.postValue(weatherMap)

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