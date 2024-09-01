package com.yapp.moa.designsystem.component.appbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.yapp.moa.designsystem.theme.BuddyConTheme

@Preview
@Composable
fun TopAppBarWithBackPreview() {
    BuddyConTheme {
        TopAppBarWithBack(
            title = "타이틀"
        )
    }
}

@Preview
@Composable
fun TopAppBarWithBackAndEditPreview() {
    BuddyConTheme {
        TopAppBarWithBackAndEdit(
            title = "타이틀"
        )
    }
}

@Preview
@Composable
fun TopAppBarWithNotificationPreview() {
    BuddyConTheme {
        TopAppBarWithNotification(
            title = "타이틀"
        )
    }
}
