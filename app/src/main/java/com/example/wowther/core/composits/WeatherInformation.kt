package com.example.wowther.core.composits

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wowther.core.data.WeatherData
import com.example.wowther.core.ext.convertTimestampToHour
import com.example.wowther.home.composits.WeatherImage

@Composable
fun WeatherInformation(weatherData: WeatherData) {
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxSize()
    ) {
        WeatherImage(iconName = weatherData.weather[0].icon)
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "${weatherData.main.temp}ºF", fontSize = 25.sp)
            Text(text = weatherData.name, fontSize = 25.sp)
            Text(text = "Coordinates (${weatherData.coord.lat}, ${weatherData.coord.lon})")
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = weatherData.weather[0].description.uppercase(), fontSize = 20.sp)
            Text(text = "MIN ${weatherData.main.temp_min} | MAX ${weatherData.main.temp_max}")
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = "Wind ${weatherData.wind.speed} KM/H")
            Text(text = "Sunrise at ${weatherData.sys.sunrise.convertTimestampToHour()}")
            Text(text = "Sunset at ${weatherData.sys.sunset.convertTimestampToHour()}")
        }
    }
}