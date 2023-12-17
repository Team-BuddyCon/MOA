package com.yapp.buddycon.navigation.startup

import com.yapp.buddycon.navigation.base.BuddyConDestination

private const val SPLASH = "splash"
private const val ONBOARDING = "onboarding"
private const val LOGIN = "login"


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
}
