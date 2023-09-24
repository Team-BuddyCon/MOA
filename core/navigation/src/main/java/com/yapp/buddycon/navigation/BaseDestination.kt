package com.yapp.buddycon.navigation

import com.yapp.buddycon.designsystem.R

const val GIFTICON = "GIFTICON"
const val MAP = "MAP"
const val MYPAGE = "MYPAGE"

sealed class BaseDestination(
    val route: String
) {
    object Gifticon : BaseDestination(route = GIFTICON), BottomDestination {
        override val drawableResId = R.drawable.ic_menu_gifticon
        override val drawableSelResId = R.drawable.ic_menu_gifticon_sel
    }

    object Map : BaseDestination(route = MAP), BottomDestination {
        override val drawableResId = R.drawable.ic_menu_map
        override val drawableSelResId = R.drawable.ic_menu_map_sel
    }

    object MyPage : BaseDestination(route = MYPAGE), BottomDestination {
        override val drawableResId = R.drawable.ic_menu_mypage
        override val drawableSelResId = R.drawable.ic_menu_gifticon_sel
    }
}
