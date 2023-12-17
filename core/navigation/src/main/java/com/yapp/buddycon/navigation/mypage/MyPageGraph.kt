package com.yapp.buddycon.navigation.mypage

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.yapp.buddycon.mypage.MyPageScreen

private const val MYPAGE_GRAPH = "mypage_graph"
fun NavGraphBuilder.mypageGraph(
    navHostController: NavHostController
) {
    navigation(
        startDestination = MyPageDestination.MyPage.route,
        route = MYPAGE_GRAPH
    ) {
        composable(MyPageDestination.MyPage.route) {
            MyPageScreen()
        }
    }
}
