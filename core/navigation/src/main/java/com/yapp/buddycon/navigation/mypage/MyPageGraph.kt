package com.yapp.buddycon.navigation.mypage

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.yapp.buddycon.mypage.MyPageScreen
import com.yapp.buddycon.navigation.MYPAGE_GRAPH

fun NavGraphBuilder.mypageGraph(
    navHostController: NavHostController
) {
    navigation(
        startDestination = MyPageDestination.MyPage.route,
        route = MYPAGE_GRAPH,
    ) {
        composable(MyPageDestination.MyPage.route) {
            MyPageScreen()
        }
    }
}
