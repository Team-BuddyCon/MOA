package com.yapp.moa.navigation.map

import com.yapp.moa.designsystem.R
import com.yapp.moa.navigation.base.BottomDestination
import com.yapp.moa.navigation.base.BuddyConDestination

private const val MAP = "지도"

sealed class MapDestination : BuddyConDestination {

    object Map : MapDestination(), BottomDestination {
        override val route = MAP
        override val drawableResId = R.drawable.ic_menu_map
        override val drawableSelResId = R.drawable.ic_menu_map_sel
    }
}
