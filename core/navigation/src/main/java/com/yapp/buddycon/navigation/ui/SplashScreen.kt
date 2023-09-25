package com.yapp.buddycon.navigation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToGifticon: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        delay(1000)
        onNavigateToGifticon()
    }

    Column {
        Text(text = "Splash")
    }
}
