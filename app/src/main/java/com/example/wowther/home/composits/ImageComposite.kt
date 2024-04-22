package com.example.wowther.home.composits

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun WeatherImage(iconName: String) {
    val painter = rememberAsyncImagePainter(
        model = "http://openweathermap.org/img/w/${iconName}.png"
    )

    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.Fit
    )
}