package com.example.wowther.home.composits

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter

@Composable
fun WeatherImage(iconName: String) {
    val imageUrl = "https://openweathermap.org/img/w/$iconName.png"

    val painter = rememberAsyncImagePainter(
        model = imageUrl
    )

    LaunchedEffect(painter) {
        Log.d("WeatherImage", "Loading image: $imageUrl")
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize(fraction = 0.3f)
        )
    }
}
