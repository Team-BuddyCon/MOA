package com.yapp.buddycon.designsystem.component.model

sealed class BuddyConButtons(
    open val action: (() -> Unit)?
) {
    sealed class Dialog(
        open val title: String,
        override val action: (() -> Unit)? = null,
    ) : BuddyConButtons(action) {
        data class Light(
            override val title: String,
            override val action: (() -> Unit)?
        ) : Dialog(title, action)

        data class Dark(
            override val title: String,
            override val action: (() -> Unit)?
        ) : Dialog(title, action)
    }
}
