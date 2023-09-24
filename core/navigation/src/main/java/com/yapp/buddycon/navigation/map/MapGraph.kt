package com.yapp.buddycon.navigation.map

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.yapp.buddycon.map.MapScreen
import com.yapp.buddycon.navigation.MAP_GRAPH

fun NavGraphBuilder.mapGraph(
    navHostController: NavHostController
) {
    navigation(
        startDestination = MapDestination.Map.route,
        route = MAP_GRAPH,
    ) {
        composable(MapDestination.Map.route) {
            MapScreen()
        }
    }
}
