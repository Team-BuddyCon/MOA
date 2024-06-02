package com.yapp.buddycon.navigation.startup

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.yapp.buddycon.navigation.base.BuddyConDestination

private const val SPLASH = "splash"
private const val ONBOARDING = "onboarding"
private const val LOGIN = "login"
private const val SIGNUP = "signup"
private const val WELCOME = "welcome"

sealed class StartUpDestination : BuddyConDestination {
    object Splash : StartUpDestination() {
        override val route = SPLASH
    }

    object OnBoarding : StartUpDestination() {
        override val route = ONBOARDING
    }

    object Login : StartUpDestination() {
        override val route = LOGIN
        const val isTestModeArg = "isTestMode"
        val routeWithArg = "${Login.route}/{$isTestModeArg}"
        val arguments = listOf(
            navArgument(isTestModeArg) {
                type = NavType.BoolType
            }
        )
    }

    object SignUp : StartUpDestination() {
        override val route = SIGNUP
    }

    object Welcome : StartUpDestination() {
        override val route = WELCOME
    }
}
