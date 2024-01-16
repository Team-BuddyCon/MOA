package com.yapp.buddycon.gifticon.available

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AvailabeGifticonScreen(
    availableGifticonViewModel: AvailableGifticonViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.LightGray)
    ) {
        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = {
                availableGifticonViewModel.getAvailableGifiticon()
            }
        ) {
            Text("test")
        }
    }
}