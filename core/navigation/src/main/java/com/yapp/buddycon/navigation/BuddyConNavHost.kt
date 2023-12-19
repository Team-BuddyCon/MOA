package com.yapp.buddycon.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yapp.buddycon.navigation.gifticon.gifticonGraph
import com.yapp.buddycon.navigation.map.mapGraph
import com.yapp.buddycon.navigation.mypage.mypageGraph
import com.yapp.buddycon.navigation.startup.StartUpDestination
import com.yapp.buddycon.startup.login.LoginScreen
import com.yapp.buddycon.startup.onboarding.OnBoardingScreen
import com.yapp.buddycon.startup.signup.SignUpScreen
import com.yapp.buddycon.startup.splash.SplashScreen

private const val ROOT_GRAPH = "root_graph"

@Composable
fun BuddyConNavHost(
    navHostController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = { BuddyConBottomBar(navHostController) }
    ) { paddingValues ->
        NavHost(
            navController = navHostController,
            startDestination = StartUpDestination.Splash.route,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            route = ROOT_GRAPH
        ) {
            composable(route = StartUpDestination.Splash.route) {
                SplashScreen(
                    onNavigateToOnBoarding = {
                        navHostController.navigate(StartUpDestination.OnBoarding.route) {
                            popUpTo(StartUpDestination.Splash.route) {
                                inclusive = true
                            }
                        }
                    },
                    onNavigateToLogin = {
                        navHostController.navigate(StartUpDestination.Login.route) {
                            popUpTo(StartUpDestination.Splash.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(route = StartUpDestination.OnBoarding.route) {
                OnBoardingScreen {
                    navHostController.navigate(StartUpDestination.Login.route) {
                        popUpTo(StartUpDestination.OnBoarding.route) {
                            inclusive = true
                        }
                    }
                }
            }

            composable(route = StartUpDestination.Login.route) {
                LoginScreen {
                    navHostController.navigate(StartUpDestination.SignUp.route)
                }
            }

            composable(route = StartUpDestination.SignUp.route) {
                SignUpScreen(
                    onBack = {
                        navHostController.popBackStack()
                    }
                )
            }

            gifticonGraph(navHostController)
            mapGraph(navHostController)
            mypageGraph(navHostController)
        }
    }
}
