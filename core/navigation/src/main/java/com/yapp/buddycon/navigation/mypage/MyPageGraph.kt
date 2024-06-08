package com.yapp.buddycon.navigation.mypage

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.yapp.buddycon.mypage.MyPageScreen
import com.yapp.buddycon.mypage.usedgifticon.UsedGifticon

private const val MYPAGE_GRAPH = "mypage_graph"

internal fun NavGraphBuilder.mypageGraph(
    navHostController: NavHostController,
    onNavigateToLogin: (Boolean) -> Unit = {}
) {
    navigation(
        startDestination = MyPageDestination.MyPage.route,
        route = MYPAGE_GRAPH
    ) {
        composable(MyPageDestination.MyPage.route) {
            MyPageScreen(
                onNavigateToUsedGifticon = {
                    navHostController.navigate(MyPageDestination.UsedGifticon.route)
                },
                onNavigateToLogin = onNavigateToLogin
            )
        }

        composable(
            route = MyPageDestination.UsedGifticon.route
        ) {
            UsedGifticon(
                onBack = { navHostController.popBackStack() }
            )
        }
    }
}
