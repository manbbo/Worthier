package com.example.wowther.core

import com.example.wowther.core.data.WeatherData
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherApiInterface {
    //2ff60286a22c3897210ad9b9ac07818a

    @GET("/weather?q={location}&appid={api-key}")
    suspend fun getWeatherByLocation(
        @Path("location") locationName: String,
        @Path("api-key") apiKey: String = ApiKeyProvider.apiKey
    ): WeatherData
//https://api.openweathermap.org/data/2.5
    @GET("/weather?lat={lat}&lon={lon}&appid={API key}")
    suspend fun getWeatherByLatLon(
        @Path("lat") latitude: String,
        @Path("lon") longitude: String,
        @Path("api-key") apiKey: String = ApiKeyProvider.apiKey
    ): WeatherData
}