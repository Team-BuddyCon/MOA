package com.yapp.moa.navigation.mypage

import com.yapp.moa.designsystem.R
import com.yapp.moa.navigation.base.BottomDestination
import com.yapp.moa.navigation.base.BuddyConDestination

private const val MYPAGE = "마이페이지"
private const val USED_GIFTICON = "used_gifticon"
private const val DELETE_MEMBER = "delete_member"
private const val TERMS = "terms"
private const val VERSION = "version"
private const val NOTIFICATION = "notification"

sealed class MyPageDestination : BuddyConDestination {

    object MyPage : MyPageDestination(), BottomDestination {
        override val route = MYPAGE
        override val drawableResId = R.drawable.ic_menu_mypage
        override val drawableSelResId = R.drawable.ic_menu_mypage_sel
    }

    object UsedGifticon : MyPageDestination() {
        override val route = USED_GIFTICON
    }

    object DeleteMember : MyPageDestination() {
        override val route = DELETE_MEMBER
    }

    object Terms : MyPageDestination() {
        override val route = TERMS
    }

    object Version : MyPageDestination() {
        override val route = VERSION
    }

    object Notification : MyPageDestination() {
        override val route = NOTIFICATION
    }
}
