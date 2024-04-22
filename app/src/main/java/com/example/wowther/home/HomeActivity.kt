package com.example.wowther.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wowther.core.WeatherViewModel
import com.example.wowther.core.composits.WeatherInformation
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
    
    var tabIndex by remember {
        mutableStateOf(0)
    }

    val tabs: List<String> = maps.map { it.value.name }

    Scaffold (
        topBar = {
            Row(modifier = Modifier.fillMaxWidth()) {
                if (maps.size > 0) {
                    IconButton(
                        onClick = {
                            // send to Search screen

                        }) {
                        Icon(Icons.Filled.Search, "")
                    }

                    ScrollableTabRow(selectedTabIndex = tabIndex) {
                        tabs.forEachIndexed { index, title ->
                            Tab(text = { Text(text = title) },
                                selected = tabIndex == index,
                                onClick = { tabIndex = index })
                        }
                    }
                }
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),) {
                if (maps.size > 0) {
                    maps.keys.forEachIndexed { index, keys ->
                        if (tabIndex == index) {
                            maps[keys]?.let {
                                WeatherInformation(weatherData = it)
                            }
                        }
                    }
                }
            }
        }
    )
}

//@Preview(showBackground = true)
//@Composable
//fun HomeCompositePreview() {
//    WowtherTheme {
//        HomeComposite()
//    }
//}
