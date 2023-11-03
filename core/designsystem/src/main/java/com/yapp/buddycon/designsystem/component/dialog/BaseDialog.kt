package com.yapp.buddycon.designsystem.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yapp.buddycon.designsystem.component.button.BaseButton
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Grey70
import com.yapp.buddycon.designsystem.theme.Paddings

val DIALOG_OUTER_CORNER_RADIUS = 20.dp
val DIALOG_OUTER_PADDING = 30.dp

@Composable
fun BaseDialog(
    dialogTitle: String,
    dialogContent: String? = null,
    dialogButtons: List<BaseButton.Dialog>? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = DIALOG_OUTER_PADDING),
        shape = RoundedCornerShape(DIALOG_OUTER_CORNER_RADIUS),
        colors = CardDefaults.cardColors(containerColor = BuddyConTheme.colors.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Paddings.xlarge,
                    vertical = Paddings.extra
                )
        ) {
            Text(
                text = dialogTitle,
                modifier = Modifier.fillMaxWidth(),
                style = BuddyConTheme.typography.title03,
                textAlign = TextAlign.Center
            )
            dialogContent?.let { content ->
                Text(
                    text = content,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Paddings.medium),
                    style = BuddyConTheme.typography.body03.copy(color = Grey70),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(Paddings.xlarge))
            dialogButtons?.let { dialogButtons ->
                Row(
                    modifier = Modifier.padding(top = Paddings.xlarge),
                    horizontalArrangement = Arrangement.spacedBy(Paddings.medium)
                ) {
                    dialogButtons.forEach { dialogButton ->
                        when (dialogButton) {
                            is BaseButton.Dialog.Light -> {
                                LightDialogButton(
                                    text = dialogButton.title,
                                    onClick = dialogButton.action
                                )
                            }

                            is BaseButton.Dialog.Dark -> {
                                DarkDialogButton(
                                    text = dialogButton.title,
                                    onClick = dialogButton.action
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
