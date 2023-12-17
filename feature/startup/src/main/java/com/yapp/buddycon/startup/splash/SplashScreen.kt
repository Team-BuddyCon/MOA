package com.yapp.buddycon.startup.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.yapp.buddycon.designsystem.theme.BuddyConColors
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.White
import kotlinx.coroutines.delay

private const val SplashIconDescription = "SplashIcon"

@Composable
fun SplashScreen(
    onNavigateToOnBoarding: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        delay(2000)
        onNavigateToOnBoarding()
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(BuddyConTheme.colors.primaryVariant)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BuddyConTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(com.yapp.buddycon.designsystem.R.drawable.ic_splash_logo),
            contentDescription = SplashIconDescription
        )
    }
}
