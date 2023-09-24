package com.yapp.buddycon.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.yapp.buddycon.navigation.gifticon.GifticonDestination
import com.yapp.buddycon.navigation.gifticon.gifticonGraph

const val ROOT_GRAPH = "root_graph"
const val GIFTICON_GRAPH = "gifticon_graph"
const val MAP = "MAP"
const val MYPAGE = "MYPAGE"

@Composable
fun BuddyConNavHost(
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navHostController,
        startDestination = GifticonDestination.Gifticon.route,
        modifier = Modifier.fillMaxSize(),
        route = ROOT_GRAPH,
    ) {
        gifticonGraph(navHostController)
    }
}
