package com.yapp.moa.designsystem.component.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.moa.designsystem.theme.BuddyConTheme

private val BuddyConButtonRadius = 12.dp
private val BuddyConButtonHeight = 54.dp

@Composable
fun BuddyConButton(
    modifier: Modifier = Modifier,
    text: String,
    containerColor: Color = BuddyConTheme.colors.primary,
    contentColor: Color = BuddyConTheme.colors.onPrimary,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(BuddyConButtonHeight),
        enabled = enabled,
        shape = RoundedCornerShape(BuddyConButtonRadius),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor)
    ) {
        Text(
            text = text,
            style = BuddyConTheme.typography.subTitle.copy(color = contentColor)
        )
    }
}

@Preview
@Composable
private fun BuddyConButtonPreview() {
    BuddyConTheme {
        BuddyConButton(
            modifier = Modifier.fillMaxWidth(),
            text = "버튼",
            onClick = {}
        )
    }
}
