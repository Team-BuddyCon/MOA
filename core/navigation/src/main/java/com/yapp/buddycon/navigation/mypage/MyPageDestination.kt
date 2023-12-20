package com.yapp.buddycon.navigation.mypage

import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.navigation.base.BottomDestination
import com.yapp.buddycon.navigation.base.BuddyConDestination

private const val MYPAGE = "mypage"

sealed class MyPageDestination : BuddyConDestination {

    object MyPage : MyPageDestination(), BottomDestination {
        override val route = MYPAGE
        override val drawableResId = R.drawable.ic_menu_mypage
        override val drawableSelResId = R.drawable.ic_menu_mypage_sel
    }
}
