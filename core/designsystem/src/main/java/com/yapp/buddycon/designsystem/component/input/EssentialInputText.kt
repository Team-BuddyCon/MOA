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
fun EssentialInputText(
    modifier: Modifier = Modifier,
    title: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit = {}
) {
    BuddyConInput(
        modifier = modifier,
        buddyConInputs = BuddyConInputs.EssentialText(
            title = title,
            placeholder = placeholder
        ),
        value = value,
        onValueChange = onValueChange
    )
}

@Preview
@Composable
private fun EssentialInputTextPreview() {
    var value by remember { mutableStateOf("") }
    BuddyConTheme {
        EssentialInputText(
            modifier = Modifier.fillMaxWidth(),
            title = "기프티콘 이름",
            placeholder = "최대 16자",
            value = value,
            onValueChange = { value = it }
        )
    }
}
