package com.yapp.buddycon.designsystem.component.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yapp.buddycon.designsystem.theme.BuddyConTheme

@Composable
fun NoEssentialInputText(
    modifier: Modifier = Modifier,
    title: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit = {}
) {
    BuddyConInput(
        modifier = modifier,
        buddyConInputs = BuddyConInputs.NoEssentialText(
            title = title,
            placeholder = placeholder
        ),
        value = value,
        onValueChange = onValueChange
    )
}

@Preview
@Composable
private fun NoEssentialInputTextPreview() {
    var value by remember { mutableStateOf("") }
    BuddyConTheme {
        NoEssentialInputText(
            modifier = Modifier.fillMaxWidth(),
            title = "메모",
            placeholder = "최대 50자 입력",
            value = value,
            onValueChange = { value = it }
        )
    }
}
