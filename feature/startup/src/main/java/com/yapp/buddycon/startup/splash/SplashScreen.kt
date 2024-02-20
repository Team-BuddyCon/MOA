package com.yapp.buddycon.startup.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import timber.log.Timber

private const val SplashIconDescription = "SplashIcon"

@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel = hiltViewModel(),
    onNavigateToOnBoarding: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
    onNavigateToGifticon: () -> Unit = {}
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(BuddyConTheme.colors.primary)

    LaunchedEffect(Unit) {
        splashViewModel.loginToken.collect {
            if (it.accessTokenExpiresIn <= System.currentTimeMillis()) {
                splashViewModel.fetchReissue()
            }
        }
    }

    LaunchedEffect(Unit) {
        Timber.d("SplashScreen Token Info : ${splashViewModel.loginToken.value}, currentTime : ${System.currentTimeMillis()}")
        if (splashViewModel.isFirstInstallation.value) {
            splashViewModel.checkIsFirstInstallation()
            onNavigateToOnBoarding()
        } else {
            if (System.currentTimeMillis() <= splashViewModel.loginToken.value.accessTokenExpiresIn) {
                onNavigateToGifticon()
            } else {
                onNavigateToLogin()
            }
        }
    }

    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .background(BuddyConTheme.colors.primary)
    )
}
