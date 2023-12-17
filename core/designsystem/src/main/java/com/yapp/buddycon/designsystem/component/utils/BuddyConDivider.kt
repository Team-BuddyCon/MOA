package com.yapp.buddycon.designsystem.component.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yapp.buddycon.designsystem.theme.Grey30

@Composable
fun DividerHorizontal(
    dividerHeight: Dp = 1.dp,
    modifier: Modifier = Modifier,
    dividerColor: Color = Grey30
) {
    Box(
        modifier = modifier
            .height(dividerHeight)
            .fillMaxWidth()
            .background(color = dividerColor)
    )
}

@Composable
fun DividerVertical(
    dividerWidth: Dp = 1.dp,
    modifier: Modifier = Modifier,
    dividerColor: Color = Grey30
) {
    Box(
        modifier = modifier
            .width(dividerWidth)
            .fillMaxHeight()
            .background(color = dividerColor)
    )
}
