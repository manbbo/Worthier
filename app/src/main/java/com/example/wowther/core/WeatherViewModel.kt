package com.example.wowther.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wowther.core.data.WeatherData
import kotlinx.coroutines.launch
import java.lang.Exception

class WeatherViewModel: ViewModel() {
    private val service = WeatherService.api
    lateinit var locationInfos: MutableLiveData<ArrayList<WeatherData>>
    lateinit var hasError: MutableLiveData<Boolean>

    fun getWeatherByLocation(lat: String, lon: String) {
        viewModelScope.launch {
            val weather = service.getWeatherByLatLon(
                lat, lon, apiKey = ""
            )
        }
    }

    fun getWeatherByName(locationName: String) {
        viewModelScope.launch {
            try {
                val weather = service.getWeatherByLocation(
                    locationName = locationName,
                    apiKey = ""
                )
            } catch (e: Exception) {

            }
        }
    }

    fun getDefaultWeatherData() {
        viewModelScope.launch {
            try {
                locationInfos.postValue(arrayListOf(
                    service.getWeatherByLocation(
                        locationName = "Montevideo",
                        apiKey = ""
                ),
                    service.getWeatherByLocation(
                        locationName = "Londres",
                        apiKey = ""
                ),
                    service.getWeatherByLocation(
                        locationName = "San Pablo",
                        apiKey = ""
                ),
                    service.getWeatherByLocation(
                        locationName = "Buenos Aires",
                        apiKey = ""
                ),
                    service.getWeatherByLocation(
                        locationName = "Munich",
                        apiKey = ""
                )))

                hasError.postValue(false)
            } catch (e: Exception) {
                hasError.postValue(true)
            }
        }
    }
}