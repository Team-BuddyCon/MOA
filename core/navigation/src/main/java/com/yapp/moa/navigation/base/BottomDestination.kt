package com.yapp.moa.navigation.base

import androidx.annotation.DrawableRes

interface BottomDestination {
    @get:DrawableRes
    val drawableResId: Int

    @get:DrawableRes
    val drawableSelResId: Int
}
