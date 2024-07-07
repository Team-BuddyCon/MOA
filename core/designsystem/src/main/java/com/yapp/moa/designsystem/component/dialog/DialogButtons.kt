package com.yapp.moa.designsystem.component.dialog

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yapp.moa.designsystem.theme.BuddyConTheme

private val DIALOG_BUTTON_HEIGHT = 46.dp
private val DIALOG_BUTTON_SHAPE_RADIUS = 12.dp

sealed class DialogButtons(
    open val title: String,
    open val action: (() -> Unit)? = null
) {
    data class Light(
        override val title: String,
        override val action: (() -> Unit)?
    ) : DialogButtons(title, action)

    data class Dark(
        override val title: String,
        override val action: (() -> Unit)?
    ) : DialogButtons(title, action)
}

@Composable
fun RowScope.LightDialogButton(
    text: String,
    onClick: (() -> Unit)? = null
) {
    Button(
        onClick = onClick ?: {},
        modifier = Modifier
            .weight(1f)
            .height(DIALOG_BUTTON_HEIGHT),
        shape = RoundedCornerShape(DIALOG_BUTTON_SHAPE_RADIUS),
        colors = ButtonDefaults.buttonColors(
            containerColor = BuddyConTheme.colors.lightDialog
        )
    ) {
        Text(
            text = text,
            color = BuddyConTheme.colors.onLightDialog,
            style = BuddyConTheme.typography.subTitle
        )
    }
}

@Composable
fun RowScope.DarkDialogButton(
    text: String,
    onClick: (() -> Unit)? = null
) {
    Button(
        onClick = onClick ?: {},
        modifier = Modifier
            .weight(1f)
            .height(DIALOG_BUTTON_HEIGHT),
        shape = RoundedCornerShape(DIALOG_BUTTON_SHAPE_RADIUS),
        colors = ButtonDefaults.buttonColors(
            containerColor = BuddyConTheme.colors.darkDialog
        )
    ) {
        Text(
            text = text,
            color = BuddyConTheme.colors.onDarkDialog,
            style = BuddyConTheme.typography.subTitle
        )
    }
}
