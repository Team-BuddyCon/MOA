package com.yapp.moa.navigation.map

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.yapp.moa.map.MapScreen
import com.yapp.moa.navigation.gifticon.GifticonDestination

private const val MAP_GRAPH = "map_graph"

internal fun NavGraphBuilder.mapGraph(
    navHostController: NavHostController
) {
    navigation(
        startDestination = MapDestination.Map.route,
        route = MAP_GRAPH
    ) {
        composable(MapDestination.Map.route) {
            MapScreen { id ->
                val fromRegister = false
                navHostController.navigate("${GifticonDestination.Detail.route}/$id/$fromRegister")
            }
        }
    }
}
