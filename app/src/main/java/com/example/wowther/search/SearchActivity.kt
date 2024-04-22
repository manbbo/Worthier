package com.example.wowther.search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.wowther.core.WeatherViewModel

class SearchActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        
    }
}

@Composable
fun SearchBarComposite() {
    var searchLocation = ""

    Row {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.Search, null)
        }
        Box (modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = searchLocation,
                onValueChange = {
                    searchLocation = it
                },
                placeholder = {
                    Text(text = "Search for a location")
                })
        }
    }
}

@Composable
fun SearchContent() {
    
}
