package com.yapp.buddycon.navigation.gifticon

import android.util.Log
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.yapp.buddycon.gifticon.GifticonScreeen
import com.yapp.buddycon.gifticon.detail.GifticonDetailScreen
import com.yapp.buddycon.gifticon.detail.GifticonDetailViewModel
import com.yapp.buddycon.gifticon.edit.GifticonEditScreen
import com.yapp.buddycon.gifticon.nearestuse.NearestUseScreen
import com.yapp.buddycon.gifticon.register.GifticonRegisterScreen

private const val GIFTICON_GRAPH = "gifticon_graph"
private const val KEY_AFTER_GIFTICON_REGISTRATION_COMPLETES = "KEY_AFTER_GIFTICON_REGISTRATION_COMPLETES"

fun NavGraphBuilder.gifticonGraph(
    navHostController: NavHostController
) {
    navigation(
        startDestination = GifticonDestination.Gifticon.route,
        route = GIFTICON_GRAPH
    ) {
        composable(GifticonDestination.Gifticon.route) { entry ->
            val afterGifticonRegistrationCompletes = entry.savedStateHandle.get<Boolean>(KEY_AFTER_GIFTICON_REGISTRATION_COMPLETES)
            Log.e("MOATest", "afterRegisterCompletes : $afterGifticonRegistrationCompletes")

            GifticonScreeen(
                onNavigateToRegister = {
                    navHostController.navigate(GifticonDestination.Register.route)
                },
                onNavigateToGifticonDetail = { gifticonId ->
                    val fromRegister = false
                    navHostController.navigate("${GifticonDestination.Detail.route}/$gifticonId/$fromRegister")
                },
                afterGifticonRegistrationCompletes = afterGifticonRegistrationCompletes
            )
        }

        composable(GifticonDestination.Register.route) { entry ->
            val parentEntry = remember(entry) { navHostController.getBackStackEntry(GifticonDestination.Gifticon.route) }
            GifticonRegisterScreen(
                gifticonViewModel = hiltViewModel(parentEntry),
                onNavigateToGifticonDetail = { id ->
                    val fromRegister = true
                    navHostController.navigate("${GifticonDestination.Detail.route}/$id/$fromRegister") {
                        popUpTo(GifticonDestination.Gifticon.route) {
                            inclusive = false
                        }
                    }
                },
                onBack = { navHostController.popBackStack() }
            )
        }

        composable(
            route = GifticonDestination.Detail.routeWithArg,
            arguments = GifticonDestination.Detail.arguments
        ) { entry ->
            val gifticonId = entry.arguments?.getInt(GifticonDestination.Detail.gifticonIdArg)
            val fromRegister = entry.arguments?.getBoolean(GifticonDestination.Detail.fromRegisterArg).also {
                navHostController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(KEY_AFTER_GIFTICON_REGISTRATION_COMPLETES, it)
            }

            GifticonDetailScreen(
                gifticonId = gifticonId,
                fromRegister = fromRegister,
                onBack = { navHostController.popBackStack() },
                onNavigateToNearestUse = { id ->
                    navHostController.navigate("${GifticonDestination.NearestUse.route}/$id")
                },
                onNavigateToGifticonEdit = { id ->
                    navHostController.navigate("${GifticonDestination.Edit.route}/$id")
                }
            )
        }

        composable(
            route = GifticonDestination.NearestUse.routeWithArg,
            arguments = GifticonDestination.NearestUse.arguments
        ) { entry ->
            val gifticonId = entry.arguments?.getInt(GifticonDestination.NearestUse.gifticonIdArg)
            NearestUseScreen(
                gifticonId = gifticonId,
                onBack = { navHostController.popBackStack() }
            )
        }

        composable(
            route = GifticonDestination.Edit.routeWithArg,
            arguments = GifticonDestination.Edit.arguments
        ) { entry ->
            val gifticonId = entry.arguments?.getInt(GifticonDestination.Edit.gifticonIdArg)
            val parentEntry = remember(entry) {
                navHostController.getBackStackEntry(GifticonDestination.Detail.routeWithArg)
            }

            GifticonEditScreen(
                gifticonDetailViewModel = hiltViewModel(parentEntry),
                gifticonId = gifticonId
            )
        }
    }
}
