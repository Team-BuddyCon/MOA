package com.yapp.buddycon.designsystem.component.appbar

sealed class BuddyConAppBars(
    open val title: String?
) {
    data class WithBack(
        override val title: String?,
        val onBack: (() -> Unit)? = null
    ) : BuddyConAppBars(title)

    data class WithBackAndEdit(
        override val title: String?,
        val onEdit: (() -> Unit)? = null,
        val onBack: (() -> Unit)? = null
    ) : BuddyConAppBars(title)

    data class WithNotification(
        override val title: String?,
        val action: (() -> Unit)? = null
    ) : BuddyConAppBars(title)

    data class ForSetting(
        override val title: String?,
        val action: (() -> Unit)? = null
    ) : BuddyConAppBars(title)
}
