package com.yapp.buddycon.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Immutable
data class BuddyConColors(
    val primary: Color,
    val onPrimary: Color,
    val primaryVariant: Color,
    val background: Color,
    val lightDialog: Color,
    val onLightDialog: Color,
    val darkDialog: Color,
    val onDarkDialog: Color,
    val snackbarBackground: Color,
    val onSnackbar: Color,
    val topAppBarColor: Color,
    val onTopAppBarColor: Color,
    val modalColor: Color
)

val LocalBuddyConColors = staticCompositionLocalOf {
    BuddyConColors(
        primary = Color.Unspecified,
        onPrimary = Color.Unspecified,
        primaryVariant = Color.Unspecified,
        background = Color.Unspecified,
        lightDialog = Color.Unspecified,
        onLightDialog = Color.Unspecified,
        darkDialog = Color.Unspecified,
        onDarkDialog = Color.Unspecified,
        snackbarBackground = Color.Unspecified,
        onSnackbar = Color.Unspecified,
        topAppBarColor = Color.Unspecified,
        onTopAppBarColor = Color.Unspecified,
        modalColor = Color.Unspecified
    )
}

val Purple80 = Color(0xFFD0BCFF)
val Purple40 = Color(0xFF6650a4)

val PurpleGrey80 = Color(0xFFCCC2DC)
val PurpleGrey40 = Color(0xFF625b71)

val Pink100 = Color(0xFFFF4E6E)
val Pink80 = Color(0xFFEFB8C8)
val Pink50 = Color(0xFFFFEDF0)
val Pink40 = Color(0xFF7D5260)
val Pink20 = Color(0xFFFFF6F8)

val Grey90 = Color(0xFF2D2D2D)
val Grey70 = Color(0xFF6F6F71)
val Grey60 = Color(0xFF838486)
val Grey50 = Color(0xFFAEAFB1)
val Grey40 = Color(0xFFCBCCCE)
val Grey30 = Color(0xFFECEDEF)
val Grey20 = Color(0xFFF2F3F5)

val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val Aureolin = Color(0xFFF9EB00)
val Temptress = Color(0xFF381E20)

@Preview(widthDp = 540)
@Composable
private fun ColorPreview() {
    BuddyConTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
        ) {
            Row {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(BuddyConTheme.colors.primary)
                    )
                    Text(
                        text = "Pink100",
                        modifier = Modifier.padding(top = 5.dp),
                        color = Grey90
                    )
                }
                Column(
                    modifier = Modifier.padding(start = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Pink50)
                    )
                    Text(
                        text = "Pink50",
                        modifier = Modifier.padding(top = 5.dp),
                        color = Grey90
                    )
                }
                Column(
                    modifier = Modifier.padding(start = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Pink20)
                    )
                    Text(
                        text = "Pink20",
                        modifier = Modifier.padding(top = 5.dp),
                        color = Grey90
                    )
                }
            }
            Row(modifier = Modifier.padding(top = 20.dp)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Black)
                    )
                    Text(
                        text = "Black",
                        modifier = Modifier.padding(top = 5.dp),
                        color = Grey90
                    )
                }
                Column(
                    modifier = Modifier.padding(start = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Grey90)
                    )
                    Text(
                        text = "Grey90",
                        modifier = Modifier.padding(top = 5.dp),
                        color = Grey90
                    )
                }
                Column(
                    modifier = Modifier.padding(start = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Grey70)
                    )
                    Text(
                        text = "Grey70",
                        modifier = Modifier.padding(top = 5.dp),
                        color = Grey90
                    )
                }
                Column(
                    modifier = Modifier.padding(start = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Grey60)
                    )
                    Text(
                        text = "Grey60",
                        modifier = Modifier.padding(top = 5.dp),
                        color = Grey90
                    )
                }
                Column(
                    modifier = Modifier.padding(start = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Grey50)
                    )
                    Text(
                        text = "Grey50",
                        modifier = Modifier.padding(top = 5.dp),
                        color = Grey90
                    )
                }
            }
            Row(modifier = Modifier.padding(top = 20.dp)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Grey40)
                    )
                    Text(
                        text = "Grey40",
                        modifier = Modifier.padding(top = 5.dp),
                        color = Grey90
                    )
                }
                Column(
                    modifier = Modifier.padding(start = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Grey30)
                    )
                    Text(
                        text = "Grey30",
                        modifier = Modifier.padding(top = 5.dp),
                        color = Grey90
                    )
                }
                Column(
                    modifier = Modifier.padding(start = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Grey20)
                    )
                    Text(
                        text = "Grey20",
                        modifier = Modifier.padding(top = 5.dp),
                        color = Grey90
                    )
                }
                Column(
                    modifier = Modifier.padding(start = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(
                        modifier = Modifier
                            .size(80.dp)
                            .border(2.dp, Grey60, CircleShape)
                            .clip(CircleShape)
                            .background(White)
                    )
                    Text(
                        text = "White",
                        modifier = Modifier.padding(top = 5.dp),
                        color = Grey90
                    )
                }
            }
        }
    }
}
