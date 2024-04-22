package com.example.wowther.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.example.wowther.ui.theme.WowtherTheme

@Composable
fun WelcomeActivity() {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "We need your location")
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeActivityPreview() {
    WowtherTheme {
        WelcomeActivity()
    }
}
