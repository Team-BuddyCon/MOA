package com.yapp.buddycon.designsystem.component.input

import androidx.annotation.DrawableRes
import com.yapp.buddycon.designsystem.R

sealed class BuddyConInputs(
    open val title: String,
    val isEssential: Boolean,
    open val placeholder: String,
    open val action: (() -> Unit)? = null
) {
    data class EssentialText(
        override val title: String,
        override val placeholder: String
    ) : BuddyConInputs(title, true, placeholder, null)

    data class EssentialSelectDate(
        override val title: String,
        override val placeholder: String,
        override val action: (() -> Unit)? = null,
        @DrawableRes val icon: Int = R.drawable.ic_calendar
    ) : BuddyConInputs(title, true, placeholder, action)

    data class EssentialSelectUsage(
        override val title: String,
        override val placeholder: String,
        override val action: (() -> Unit)? = null,
        @DrawableRes val icon: Int = R.drawable.ic_down_arrow
    ) : BuddyConInputs(title, true, placeholder, action)

    data class NoEssentialText(
        override val title: String,
        override val placeholder: String
    ) : BuddyConInputs(title, false, placeholder, null)
}
