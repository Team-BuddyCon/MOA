package com.yapp.moa.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amplitude.android.Amplitude
import com.yapp.moa.navigation.gifticon.GifticonDestination
import com.yapp.moa.navigation.gifticon.gifticonGraph
import com.yapp.moa.navigation.map.mapGraph
import com.yapp.moa.navigation.mypage.mypageGraph
import com.yapp.moa.navigation.startup.StartUpDestination
import com.yapp.moa.startup.login.LoginScreen
import com.yapp.moa.startup.onboarding.OnBoardingScreen
import com.yapp.moa.startup.signup.SignUpScreen
import com.yapp.moa.startup.splash.SplashScreen
import com.yapp.moa.startup.welcome.WelcomeScreen

private const val ROOT_GRAPH = "root_graph"

@Composable
fun BuddyConNavHost(
    navHostController: NavHostController = rememberNavController(),
    amplitude: Amplitude
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
                    },
                    onNavigateToGifticon = {
                        navHostController.navigate(GifticonDestination.Gifticon.route) {
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

            composable(route = StartUpDestination.Login.route) { entry ->
                LoginScreen(
                    onNavigateToSignUp = {
                        navHostController.navigate(StartUpDestination.SignUp.route)
                    },
                    onNavigateToGifticon = {
                        navHostController.navigate(GifticonDestination.Gifticon.route) {
                            popUpTo(StartUpDestination.Login.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(route = StartUpDestination.SignUp.route) {
                SignUpScreen(
                    onNavigateToWelcome = {
                        navHostController.navigate(StartUpDestination.Welcome.route) {
                            popUpTo(StartUpDestination.Login.route) {
                                inclusive = true
                            }
                        }
                    },
                    onBack = {
                        navHostController.popBackStack()
                    }
                )
            }

            composable(route = StartUpDestination.Welcome.route) {
                WelcomeScreen {
                    navHostController.navigate(GifticonDestination.Gifticon.route) {
                        popUpTo(StartUpDestination.Welcome.route) {
                            inclusive = true
                        }
                    }
                }
            }

            gifticonGraph(navHostController, amplitude)
            mapGraph(navHostController)
            mypageGraph(navHostController) {
                navHostController.navigate(StartUpDestination.Login.route) {
                    popUpTo(GifticonDestination.Gifticon.route) {
                        inclusive = true
                    }
                }
            }
        }
    }
}
