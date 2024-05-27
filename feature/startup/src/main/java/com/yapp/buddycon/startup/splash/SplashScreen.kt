package com.yapp.buddycon.startup.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import kotlinx.coroutines.delay

private const val SplashIconDescription = "SplashIcon"

@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel = hiltViewModel(),
    onNavigateToOnBoarding: () -> Unit = {},
    onNavigateToLogin: (Boolean) -> Unit = {},
    onNavigateToGifticon: () -> Unit = {}
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(BuddyConTheme.colors.primary)

    val isTestMode by splashViewModel.isTestMode.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        splashViewModel.loginToken.collect {
            if (it.accessTokenExpiresIn != Long.MIN_VALUE) {
                if (it.accessTokenExpiresIn <= System.currentTimeMillis()) {
                    splashViewModel.fetchReissue()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        delay(100)
        if (splashViewModel.isFirstInstallation.value) {
            splashViewModel.checkIsFirstInstallation()
            onNavigateToOnBoarding()
        } else {
            if (System.currentTimeMillis() <= splashViewModel.loginToken.value.accessTokenExpiresIn) {
                onNavigateToGifticon()
            } else {
                onNavigateToLogin(isTestMode)
            }
        }
    }

    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .background(BuddyConTheme.colors.primary)
    )
}
