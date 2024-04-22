package com.example.wowther.search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.wowther.core.WeatherViewModel
import com.example.wowther.core.composits.WeatherInformation
import com.example.wowther.ui.theme.WowtherTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val map = viewModel.locationInfos.observeAsState(initial = null).value

            WowtherTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(topBar = {
                        SearchBarComposite(viewModel)
                    },
                        content = { innerPadding ->
                            Column(
                                modifier = Modifier
                                    .padding(innerPadding),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                            ) {
                                map?.let {
                                    WeatherInformation(weatherData = it)
                                }
                            }
                        })
                }
            }
        }
    }
}

@Composable
fun SearchBarComposite(viewModel: WeatherViewModel) {
    var searchLocation by remember { mutableStateOf("") }
    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    var job: Job? by remember { mutableStateOf(null) }

    Row {
        IconButton(onClick = {
            (context as? ComponentActivity)?.finish()
        }) {
            Icon(Icons.Filled.ArrowBack, null)
        }
        Box (modifier = Modifier.fillMaxWidth()) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                enabled = true,
                value = searchLocation,
                onValueChange = { input ->
                    searchLocation = input
                    job?.cancel()
                    job = scope.launch {
                        delay(2000)
                        viewModel.getWeatherByName(input)
                    }
                },
                maxLines = 1,
                placeholder = {
                    Text(text = "Search for a location")
                }
            )
        }
    }
}
