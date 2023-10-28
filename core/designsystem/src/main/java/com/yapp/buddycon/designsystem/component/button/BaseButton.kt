package com.yapp.buddycon.designsystem.component.button

sealed class BaseButton(
    open val action: (() -> Unit)?
) {
    sealed class Dialog(
        open val title: String,
        override val action: (() -> Unit)? = null,
    ) : BaseButton(action) {
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
