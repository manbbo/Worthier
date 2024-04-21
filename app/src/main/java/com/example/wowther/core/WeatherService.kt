package com.example.wowther.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherService {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5"

    // icon: http://openweathermap.org/img/w/{iconname}.png
    val api: WeatherApiInterface by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(WeatherApiInterface::class.java)
    }
}