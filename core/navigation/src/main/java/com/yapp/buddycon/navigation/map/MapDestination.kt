package com.yapp.buddycon.navigation.map

import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.navigation.base.BottomDestination
import com.yapp.buddycon.navigation.base.BuddyConDestination

const val MAP = "map"

sealed interface MapDestination : BuddyConDestination {

    object Map : MapDestination, BottomDestination {
        override val route = MAP
        override val drawableResId = R.drawable.ic_menu_map
        override val drawableSelResId = R.drawable.ic_menu_map_sel
    }
}
