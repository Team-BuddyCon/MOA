package com.yapp.buddycon.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Immutable
data class BuddyConTypography(
    val largeTitle: TextStyle,
    val title01: TextStyle,
    val title02: TextStyle,
    val title03: TextStyle,
    val title05: TextStyle,
    val subTitle: TextStyle,
    val body01: TextStyle,
    val body02: TextStyle,
    val body03: TextStyle,
    val body04: TextStyle,
    val body05: TextStyle,
    val body06: TextStyle
)

val LocalBuddyConTypography = staticCompositionLocalOf {
    BuddyConTypography(
        largeTitle = TextStyle.Default,
        title01 = TextStyle.Default,
        title02 = TextStyle.Default,
        title03 = TextStyle.Default,
        title05 = TextStyle.Default,
        subTitle = TextStyle.Default,
        body01 = TextStyle.Default,
        body02 = TextStyle.Default,
        body03 = TextStyle.Default,
        body04 = TextStyle.Default,
        body05 = TextStyle.Default,
        body06 = TextStyle.Default
    )
}

@Preview(showBackground = true, widthDp = 540)
@Composable
private fun TypographyPreview() {
    BuddyConTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Row {
                Text(
                    text = "Large Title",
                    modifier = Modifier.width(142.dp),
                    style = BuddyConTheme.typography.largeTitle
                )
                Text(
                    text = "Pretendard / Medium / 30",
                    modifier = Modifier.padding(start = 54.dp),
                    style = BuddyConTheme.typography.largeTitle
                )
            }
            Row(Modifier.padding(top = 20.dp)) {
                Text(
                    text = "Title 01",
                    modifier = Modifier.width(142.dp),
                    style = BuddyConTheme.typography.title01
                )
                Text(
                    text = "Pretendard / Bold / 24",
                    modifier = Modifier.padding(start = 54.dp),
                    style = BuddyConTheme.typography.title01
                )
            }
            Row(Modifier.padding(top = 20.dp)) {
                Text(
                    text = "Title 02",
                    modifier = Modifier.width(142.dp),
                    style = BuddyConTheme.typography.title02
                )
                Text(
                    text = "Pretendard / Bold / 22",
                    modifier = Modifier.padding(start = 54.dp),
                    style = BuddyConTheme.typography.title02
                )
            }
            Row(Modifier.padding(top = 20.dp)) {
                Text(
                    text = "Title 03",
                    modifier = Modifier.width(142.dp),
                    style = BuddyConTheme.typography.title03
                )
                Text(
                    text = "Pretendard / Bold / 22",
                    modifier = Modifier.padding(start = 54.dp),
                    style = BuddyConTheme.typography.title03
                )
            }
            Row(Modifier.padding(top = 20.dp)) {
                Text(
                    text = "Title 05",
                    modifier = Modifier.width(142.dp),
                    style = BuddyConTheme.typography.title05
                )
                Text(
                    text = "Pretendard / Regular / 18",
                    modifier = Modifier.padding(start = 54.dp),
                    style = BuddyConTheme.typography.title05
                )
            }
            Row(Modifier.padding(top = 20.dp)) {
                Text(
                    text = "Sub Title",
                    modifier = Modifier.width(142.dp),
                    style = BuddyConTheme.typography.subTitle
                )
                Text(
                    text = "Pretendard / Bold / 16",
                    modifier = Modifier.padding(start = 54.dp),
                    style = BuddyConTheme.typography.subTitle
                )
            }
            Row(Modifier.padding(top = 20.dp)) {
                Text(
                    text = "Body 01",
                    modifier = Modifier.width(142.dp),
                    style = BuddyConTheme.typography.body01
                )
                Text(
                    text = "Pretendard / Regular / 16",
                    modifier = Modifier.padding(start = 54.dp),
                    style = BuddyConTheme.typography.body01
                )
            }
            Row(Modifier.padding(top = 20.dp)) {
                Text(
                    text = "Body 02",
                    modifier = Modifier.width(142.dp),
                    style = BuddyConTheme.typography.body02
                )
                Text(
                    text = "Pretendard / Bold / 14",
                    modifier = Modifier.padding(start = 54.dp),
                    style = BuddyConTheme.typography.body02
                )
            }
            Row(Modifier.padding(top = 20.dp)) {
                Text(
                    text = "Body 03",
                    modifier = Modifier.width(142.dp),
                    style = BuddyConTheme.typography.body03
                )
                Text(
                    text = "Pretendard / Regular / 14",
                    modifier = Modifier.padding(start = 54.dp),
                    style = BuddyConTheme.typography.body03
                )
            }
            Row(Modifier.padding(top = 20.dp)) {
                Text(
                    text = "Body 04",
                    modifier = Modifier.width(142.dp),
                    style = BuddyConTheme.typography.body04
                )
                Text(
                    text = "Pretendard / Bold / 12",
                    modifier = Modifier.padding(start = 54.dp),
                    style = BuddyConTheme.typography.body04
                )
            }
            Row(Modifier.padding(top = 20.dp)) {
                Text(
                    text = "Body 05",
                    modifier = Modifier.width(142.dp),
                    style = BuddyConTheme.typography.body05
                )
                Text(
                    text = "Pretendard / Regular / 12",
                    modifier = Modifier.padding(start = 54.dp),
                    style = BuddyConTheme.typography.body05
                )
            }
            Row(Modifier.padding(top = 20.dp)) {
                Text(
                    text = "Body 06",
                    modifier = Modifier.width(142.dp),
                    style = BuddyConTheme.typography.body06
                )
                Text(
                    text = "Pretendard / Medium / 10",
                    modifier = Modifier.padding(start = 54.dp),
                    style = BuddyConTheme.typography.body06
                )
            }
        }
    }
}
