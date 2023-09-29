package com.yapp.buddycon.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yapp.buddycon.navigation.component.BuddyConBottomBar
import com.yapp.buddycon.navigation.gifticon.GifticonDestination
import com.yapp.buddycon.navigation.gifticon.gifticonGraph
import com.yapp.buddycon.navigation.map.mapGraph
import com.yapp.buddycon.navigation.mypage.mypageGraph
import com.yapp.buddycon.navigation.startup.SplashDestination
import com.yapp.buddycon.navigation.ui.SplashScreen

const val ROOT_GRAPH = "root_graph"
const val GIFTICON_GRAPH = "gifticon_graph"
const val MAP_GRAPH = "map_graph"
const val MYPAGE_GRAPH = "mypage_graph"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuddyConNavHost(
    navHostController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = { BuddyConBottomBar(navHostController) },
    ) { paddingValues ->
        NavHost(
            navController = navHostController,
            startDestination = SplashDestination.route,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            route = ROOT_GRAPH,
        ) {
            composable(route = SplashDestination.route) {
                SplashScreen {
                    navHostController.navigate(GifticonDestination.Gifticon.route)
                }
            }

            gifticonGraph(navHostController)
            mapGraph(navHostController)
            mypageGraph(navHostController)
        }
    }
}
