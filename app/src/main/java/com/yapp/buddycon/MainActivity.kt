package com.yapp.buddycon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.navigation.BuddyConNavHost
import dagger.hilt.android.AndroidEntryPoint

object NoRippleTheme : RippleTheme {

    @Composable
    override fun defaultColor(): Color = Color.Transparent

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0f, 0f, 0f, 0f)
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        setContent {
            BuddyConApp()
        }
    }
}

@Composable
private fun BuddyConApp() {
    BuddyConTheme() {
        CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
            BuddyConNavHost()
        }
    }
}
