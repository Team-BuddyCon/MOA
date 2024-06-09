package com.yapp.buddycon.navigation.mypage

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.yapp.buddycon.mypage.MyPageScreen
import com.yapp.buddycon.mypage.delete.MemberDeleteScreen
import com.yapp.buddycon.mypage.terms.MyPageTermsScreen
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
                onNavigateToLogin = onNavigateToLogin,
                onNavigateToDeleteMember = {
                    navHostController.navigate(MyPageDestination.DeleteMember.route)
                },
                onNavigateToTerms = {
                    navHostController.navigate(MyPageDestination.Terms.route)
                }
            )
        }

        composable(route = MyPageDestination.UsedGifticon.route) {
            UsedGifticon(
                onBack = { navHostController.popBackStack() }
            )
        }

        composable(route = MyPageDestination.DeleteMember.route) {
            MemberDeleteScreen(
                onBack = { navHostController.popBackStack() }
            )
        }

        composable(route = MyPageDestination.Terms.route) {
            MyPageTermsScreen {
                navHostController.popBackStack()
            }
        }
    }
}
