package com.example.wowther.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LifecycleOwner
import com.example.wowther.core.WeatherViewModel
import com.example.wowther.home.composits.WeatherImage
import com.example.wowther.ui.theme.WowtherTheme

class HomeActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initializeWeatherData()

        setContent {
            WowtherTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    HomeComposite(viewModel)
                }
            }
        }
    }
}

@Composable
fun HomeComposite(viewModel: WeatherViewModel) {
    val maps = viewModel.locationInfos.observeAsState(initial = emptyMap()).value

    Column {
        maps.forEach { location ->
            Card {
                Text(text = location.value.name)
            }
        }
    }
//    WeatherImage(iconName = "10d")
//    LazyColumn {
//        items(weather.)
//    }
}

//@Preview(showBackground = true)
//@Composable
//fun HomeCompositePreview() {
//    WowtherTheme {
//        HomeComposite()
//    }
//}
