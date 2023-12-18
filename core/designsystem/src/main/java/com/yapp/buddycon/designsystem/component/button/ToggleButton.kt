package com.yapp.buddycon.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Grey40

private val ToggleButtonWidthSize = 40.dp
private val ToggleButtonHeightSize = 24.dp
private val ToggleButtonRadius = 100.dp
private val ToggleButtonInnerPadding = 2.dp
private val ToggleButtonInnerCircleSize = 20.dp

@Composable
fun ToggleButton(
    isSelected: Boolean,
    onClick: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .size(
                width = ToggleButtonWidthSize,
                height = ToggleButtonHeightSize
            )
            .background(
                color = if (isSelected) BuddyConTheme.colors.primary else Grey40,
                shape = RoundedCornerShape(ToggleButtonRadius)
            )
            .clickable { onClick(isSelected.not()) }
            .padding(ToggleButtonInnerPadding),
        contentAlignment = if (isSelected) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Spacer(
            modifier = Modifier
                .size(ToggleButtonInnerCircleSize)
                .background(BuddyConTheme.colors.background, CircleShape)
        )
    }
}

@Preview
@Composable
private fun ToggleButtonPreview() {
    var isSelected by remember { mutableStateOf(false) }
    BuddyConTheme {
        ToggleButton(
            isSelected = isSelected
        ) {
            isSelected = it
        }
    }
}