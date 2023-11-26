package com.yapp.buddycon.designsystem.component.utils

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun SpacerHorizontal(width: Dp) {
    Spacer(
        modifier = Modifier
            .width(width)
            .height(IntrinsicSize.Min)
    )
}

@Composable
fun SpacerVertical(height: Dp) {
    Spacer(
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .height(height)
    )
}
