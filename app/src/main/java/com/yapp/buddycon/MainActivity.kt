package com.yapp.buddycon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.navigation.BuddyConNavHost
import dagger.hilt.android.AndroidEntryPoint

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
    BuddyConTheme {
        BuddyConNavHost()
    }
}
