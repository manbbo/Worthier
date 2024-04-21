package com.example.wowther.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wowther.core.data.WeatherData
import kotlinx.coroutines.launch
import java.lang.Exception

class WeatherViewModel: ViewModel() {
    private val service = WeatherService.api
    lateinit var locationInfos: MutableLiveData<MutableMap<String, WeatherData>>
    lateinit var hasError: MutableLiveData<Boolean>

    fun getWeatherByLocation(lat: String, lon: String) {
        viewModelScope.launch {
            try {
                val weather = service.getWeatherByLatLon(
                    lat, lon
                )

                addWeatherToMap(weather)

                hasError.postValue(false)
            } catch (e: Exception) {
                hasError.postValue(true)
            }
        }
    }

    fun getWeatherByName(locationName: String) {
        viewModelScope.launch {
            try {
                val weather = service.getWeatherByLocation(
                    locationName = locationName
                )

                addWeatherToMap(weather)

                hasError.postValue(false)
            } catch (e: Exception) {
                hasError.postValue(true)
            }
        }
    }

    private fun addWeatherToMap(weatherData: WeatherData) {
        if (::locationInfos.isInitialized)
            locationInfos.value?.let {
                it[weatherData.name] = weatherData
            }
        else
            locationInfos.postValue(mutableMapOf(Pair(weatherData.name, weatherData)))
    }

    fun initializeWeatherData() {
        viewModelScope.launch {
            try {
                locationInfos.postValue(mutableMapOf(
                    Pair("Montevideo", service.getWeatherByLocation(
                        locationName = "Montevideo"
                    )),
                    Pair("Londres", service.getWeatherByLocation(
                        locationName = "Londres"
                    )),
                    Pair("San Pablo", service.getWeatherByLocation(
                        locationName = "San Pablo"
                    )),
                    Pair("Buenos Aires", service.getWeatherByLocation(
                        locationName = "Buenos Aires"
                    )),
                    Pair("Munich", service.getWeatherByLocation(
                        locationName = "Munich"
                    ))))

                hasError.postValue(false)
            } catch (e: Exception) {
                hasError.postValue(true)
            }
        }
    }
}