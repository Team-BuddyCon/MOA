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
    systemUiController.setStatusBarColor(BuddyConTheme.colors.primaryVariant)

    LaunchedEffect(Unit) {
        splashViewModel.loginToken.collect {
            if (it.accessTokenExpiresIn <= System.currentTimeMillis()) {
                splashViewModel.fetchReissue()
            }
        }
    }

    LaunchedEffect(Unit) {
        delay(2000)
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BuddyConTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(com.yapp.buddycon.designsystem.R.drawable.ic_logo),
            contentDescription = SplashIconDescription
        )
    }
}
