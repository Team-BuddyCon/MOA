package com.yapp.buddycon.navigation.gifticon

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.yapp.buddycon.gifticon.GifticonScreeen
import com.yapp.buddycon.navigation.GIFTICON_GRAPH

fun NavGraphBuilder.gifticonGraph(
    navHostController: NavHostController
) {
    navigation(
        startDestination = GifticonDestination.Gifticon.route,
        route = GIFTICON_GRAPH,
    ) {
        composable(GifticonDestination.Gifticon.route) {
            GifticonScreeen()
        }
    }
}
