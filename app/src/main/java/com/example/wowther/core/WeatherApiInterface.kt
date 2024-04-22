package com.example.wowther.core

import com.example.wowther.core.data.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiInterface {
    @GET("weather")
    suspend fun getWeatherByLocation(
        @Query("q") locationName: String,
        @Query("appid") apiKey: String = ApiKeyProvider.apiKey
    ): WeatherData

    @GET("weather")
    suspend fun getWeatherByLatLon(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") apiKey: String = ApiKeyProvider.apiKey
    ): WeatherData
}
