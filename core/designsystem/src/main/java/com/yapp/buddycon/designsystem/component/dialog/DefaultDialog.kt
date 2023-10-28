package com.yapp.buddycon.designsystem.component.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.yapp.buddycon.designsystem.component.button.BaseButton
import com.yapp.buddycon.designsystem.theme.BuddyConTheme

@Composable
fun DefaultDialog(
    dialogTitle: String,
    dismissText: String,
    confirmText: String,
    dialogContent: String? = null,
    onDismiss: (() -> Unit)? = null,
    onConfirm: (() -> Unit)? = null
) {
    BaseDialog(
        dialogTitle = dialogTitle,
        dialogContent = dialogContent,
        dialogButtons = listOf(
            BaseButton.Dialog.Dark(
                title = dismissText,
                action = onDismiss
            ),
            BaseButton.Dialog.Light(
                title = confirmText,
                action = onConfirm
            )
        )
    )
}

@Preview
@Composable
private fun DefaultDialogPreview() {
    BuddyConTheme {
        DefaultDialog(
            dialogTitle = "제목입니다",
            dismissText = "계속 작성",
            confirmText = "나가기"
        )
    }
}

@Preview
@Composable
private fun DefaultDialogWithContentPreview() {
    BuddyConTheme {
        DefaultDialog(
            dialogTitle = "제목입니다",
            dismissText = "취소",
            confirmText = "허용하기",
            dialogContent = "추가설명\n추가설명"
        )
    }
}