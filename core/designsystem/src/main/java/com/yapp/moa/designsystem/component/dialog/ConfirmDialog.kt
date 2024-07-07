package com.yapp.moa.designsystem.component.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.yapp.moa.designsystem.theme.BuddyConTheme
import com.yapp.moa.designsystem.R

@Composable
fun ConfirmDialog(
    dialogTitle: String,
    dialogContent: String? = null,
    onClick: (() -> Unit)? = null,
    onDismissRequest: () -> Unit = {},
    buttonText: String = stringResource(id = R.string.confirm)
) {
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onDismissRequest
    ) {
        BaseDialog(
            dialogTitle = dialogTitle,
            dialogContent = dialogContent,
            dialogButtons = listOf(
                DialogButtons.Light(
                    title = buttonText,
                    action = onClick
                )
            )
        )
    }
}

@Preview
@Composable
private fun ConfirmDialogPreview() {
    BuddyConTheme {
        ConfirmDialog(
            dialogTitle = "제목입니다"
        )
    }
}

@Preview
@Composable
private fun ConfirmDialogWithContentPreview() {
    BuddyConTheme {
        ConfirmDialog(
            dialogTitle = "제목입니다",
            dialogContent = "추가설명\n추가설명"
        )
    }
}
