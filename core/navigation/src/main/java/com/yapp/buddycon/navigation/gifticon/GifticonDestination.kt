package com.yapp.buddycon.navigation.gifticon

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.navigation.base.BottomDestination
import com.yapp.buddycon.navigation.base.BuddyConDestination

private const val GIFTICON = "gifticon"
private const val GIFTICON_REGISTER = "gifticon_register"
private const val GIFTICON_DETAIL = "gifticon_detail"

sealed class GifticonDestination : BuddyConDestination {

    object Gifticon : GifticonDestination(), BottomDestination {
        override val route = GIFTICON
        override val drawableResId = R.drawable.ic_menu_gifticon
        override val drawableSelResId = R.drawable.ic_menu_gifticon_sel
    }

    object Register : GifticonDestination() {
        override val route = GIFTICON_REGISTER
    }

    object Detail : GifticonDestination() {
        override val route = GIFTICON_DETAIL
        const val gifticonIdArg = "gifticonId"
        const val fromRegisterArg = "fromRegister"
        val routeWithArg = "$route/{$gifticonIdArg}/{$fromRegisterArg}"
        val arguments = listOf(
            navArgument(gifticonIdArg) {
                type = NavType.IntType
            },
            navArgument(fromRegisterArg) {
                type = NavType.BoolType
                defaultValue = false
            }
        )
    }
}
