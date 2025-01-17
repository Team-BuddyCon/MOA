package com.yapp.moa.navigation.mypage

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.yapp.moa.mypage.MyPageScreen
import com.yapp.moa.mypage.delete.MemberDeleteScreen
import com.yapp.moa.mypage.notification.MyPageNotificationScreen
import com.yapp.moa.mypage.terms.MyPageTermsScreen
import com.yapp.moa.mypage.usedgifticon.UsedGifticon
import com.yapp.moa.mypage.version.MyPageVersionScreen

private const val MYPAGE_GRAPH = "mypage_graph"

internal fun NavGraphBuilder.mypageGraph(
    navHostController: NavHostController,
    onNavigateToLogin: () -> Unit = {}
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
                },
                onNavigateToVersion = {
                    navHostController.navigate(MyPageDestination.Version.route)
                },
                onNavigateToNotification = {
                    navHostController.navigate(MyPageDestination.Notification.route)
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
                onNavigateToLogin = onNavigateToLogin,
                onBack = { navHostController.popBackStack() }
            )
        }

        composable(route = MyPageDestination.Terms.route) {
            MyPageTermsScreen {
                navHostController.popBackStack()
            }
        }

        composable(route = MyPageDestination.Version.route) {
            MyPageVersionScreen {
                navHostController.popBackStack()
            }
        }

        composable(route = MyPageDestination.Notification.route) {
            MyPageNotificationScreen {
                navHostController.popBackStack()
            }
        }
    }
}
