package com.yapp.moa.designsystem.component.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.yapp.moa.designsystem.theme.BuddyConTheme

@Composable
fun DefaultDialog(
    dialogTitle: String,
    dismissText: String,
    confirmText: String,
    dialogContent: String? = null,
    onConfirm: (() -> Unit)? = null,
    onDismissRequest: (() -> Unit) = {}
) {
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onDismissRequest
    ) {
        BaseDialog(
            dialogTitle = dialogTitle,
            dialogContent = dialogContent,
            dialogButtons = listOf(
                DialogButtons.Dark(
                    title = dismissText,
                    action = onDismissRequest
                ),
                DialogButtons.Light(
                    title = confirmText,
                    action = onConfirm
                )
            )
        )
    }
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
