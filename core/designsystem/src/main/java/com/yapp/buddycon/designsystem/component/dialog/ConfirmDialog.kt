package com.yapp.buddycon.designsystem.component.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.theme.BuddyConTheme

@Composable
fun ConfirmDialog(
    dialogTitle: String,
    dialogContent: String? = null,
    onClick: (() -> Unit)? = null
) {
    BaseDialog(
        dialogTitle = dialogTitle,
        dialogContent = dialogContent,
        dialogButtons = listOf(
            DialogButtons.Light(
                title = stringResource(id = R.string.confirm),
                action = onClick
            )
        )
    )
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
