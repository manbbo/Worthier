package com.example.wowther.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.wowther.core.WeatherViewModel
import com.example.wowther.core.composits.ErrorScreen
import com.example.wowther.core.composits.LoadingScreen
import com.example.wowther.core.composits.WeatherInformation
import com.example.wowther.core.ext.decimalFormatToString
import com.example.wowther.search.SearchActivity
import com.example.wowther.ui.theme.WowtherTheme

class HomeActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val locationMgr = LocalContext.current.getSystemService(
                Context.LOCATION_SERVICE
            ) as LocationManager

            val location = getLocation(locationMgr)

            location?.let {
                viewModel.initializeWeatherData(
                    it.latitude.decimalFormatToString(),
                    it.longitude.decimalFormatToString()
                )
            }

            WowtherTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeComposite(viewModel)
                }
            }
        }
    }
}

@Composable
fun getLocation(locationManager: LocationManager): Location? {
    var location: Location? by remember { mutableStateOf(null) }

    val lifecycleOwner = LocalLifecycleOwner.current
    val localContext = LocalContext.current

    val locationListener = remember {
        object : android.location.LocationListener {
            override fun onLocationChanged(newLocation: Location) {
                location = newLocation
                locationManager.removeUpdates(this)
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        }
    }

    LaunchedEffect(key1 = lifecycleOwner) {
        if (ActivityCompat.checkSelfPermission(
                localContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                localContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: check if theres still permission

            return@LaunchedEffect
        }

        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null)
    }

    return location
}

@Composable
fun HomeComposite(viewModel: WeatherViewModel) {
    val maps = viewModel.initialLocationInfos.observeAsState(initial = emptyMap()).value
    val errorState = viewModel.hasError.observeAsState(initial = false).value

    if (!errorState) {
        if (maps.isNotEmpty()) {
            var tabIndex by remember {
                mutableStateOf(0)
            }

            val tabs: List<String> = maps.map { it.value.name }
            val context = LocalContext.current

            Scaffold (
                topBar = {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        if (maps.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    val intent = Intent(context, SearchActivity::class.java)
                                    context.startActivity(intent)
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
                        if (maps.isNotEmpty()) {
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
        } else {
            LoadingScreen()
        }
    } else {
        ErrorScreen()
    }
}
