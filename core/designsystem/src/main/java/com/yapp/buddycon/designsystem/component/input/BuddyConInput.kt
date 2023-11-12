package com.yapp.buddycon.designsystem.component.input

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Grey30
import com.yapp.buddycon.designsystem.theme.Grey40
import com.yapp.buddycon.designsystem.theme.Grey50
import com.yapp.buddycon.designsystem.theme.Grey70
import com.yapp.buddycon.designsystem.theme.Grey90

private val INPUT_HORIZONTAL_PADDING = 16.dp
private val INPUT_TOP_PADDING = 16.dp
private val INPUT_BOTTOM_PADDING = 4.dp
private val INPUT_SPACING_BETWEEN_TEXT_AND_TEXTFIELD = 12.dp
private val INPUT_SPACING_BETWEEN_TEXTFIELD_AND_UNDERLINE = 8.dp
private val INPUT_ICON_SIZE = 24.dp

@Composable
internal fun BuddyConInput(
    modifier: Modifier = Modifier,
    buddyConInputs: BuddyConInputs,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = INPUT_HORIZONTAL_PADDING)
            .padding(top = INPUT_TOP_PADDING, bottom = INPUT_BOTTOM_PADDING),
    ) {
        Text(
            text = buildAnnotatedString {
                append(buddyConInputs.title)
                if (buddyConInputs.isEssential) {
                    withStyle(style = SpanStyle(Color.Red)) {
                        append(" *")
                    }
                }
            },
            style = BuddyConTheme.typography.body03.copy(color = Grey70)
        )
        Spacer(Modifier.size(INPUT_SPACING_BETWEEN_TEXT_AND_TEXTFIELD))
        Row(
            modifier = Modifier.clickable { buddyConInputs.action?.invoke() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = when (buddyConInputs) {
                    is BuddyConInputs.EssentialText -> if (value.length > 16) value.substring(0, 16) else value
                    is BuddyConInputs.NoEssentialText -> if (value.length > 50) value.substring(0, 50) else value
                    else -> value
                },
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f),
                enabled = (buddyConInputs is BuddyConInputs.EssentialText) || (buddyConInputs is BuddyConInputs.NoEssentialText),
                textStyle = BuddyConTheme.typography.subTitle.copy(
                    color = Grey90,
                ),
                singleLine = buddyConInputs !is BuddyConInputs.NoEssentialText,
                maxLines = if (buddyConInputs is BuddyConInputs.NoEssentialText) Int.MAX_VALUE else 1
            ) { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = buddyConInputs.placeholder,
                        style = BuddyConTheme.typography.subTitle.copy(color = Grey40)
                    )
                }
                innerTextField()
            }
            if (buddyConInputs is BuddyConInputs.EssentialSelectDate) {
                Icon(
                    painter = painterResource(buddyConInputs.icon),
                    contentDescription = buddyConInputs.title,
                    modifier = Modifier.size(INPUT_ICON_SIZE),
                    tint = Grey50
                )
            }
            if (buddyConInputs is BuddyConInputs.EssentialSelectUsage) {
                Icon(
                    painter = painterResource(buddyConInputs.icon),
                    contentDescription = buddyConInputs.title,
                    modifier = Modifier.size(INPUT_ICON_SIZE),
                    tint = Grey50
                )
            }
        }
        Spacer(Modifier.size(INPUT_SPACING_BETWEEN_TEXTFIELD_AND_UNDERLINE))
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Grey30)
        )
    }
}
