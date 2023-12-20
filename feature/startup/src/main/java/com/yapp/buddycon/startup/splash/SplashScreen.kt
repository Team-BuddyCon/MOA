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
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import kotlinx.coroutines.delay

private const val SplashIconDescription = "SplashIcon"

@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel = hiltViewModel(),
    onNavigateToOnBoarding: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {}
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(BuddyConTheme.colors.primaryVariant)

    LaunchedEffect(Unit) {
        delay(2000)
        if (splashViewModel.isFirstInstallation.value) {
            onNavigateToLogin()
        } else {
            splashViewModel.checkIsFirstInstallation()
            onNavigateToOnBoarding()
        }
    }

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
