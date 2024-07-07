package com.yapp.moa.navigation.startup

import com.yapp.moa.navigation.base.BuddyConDestination

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
    }

    object SignUp : StartUpDestination() {
        override val route = SIGNUP
    }

    object Welcome : StartUpDestination() {
        override val route = WELCOME
    }
}
