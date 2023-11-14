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
fun EssentialInputSelectUsage(
    modifier: Modifier = Modifier,
    title: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit = {}
) {
    var value by remember { mutableStateOf("") }
    BuddyConInput(
        modifier = modifier,
        buddyConInputs = BuddyConInputs.EssentialSelectUsage(
            title = title,
            placeholder = placeholder
        ),
        value = value,
        onValueChange = { value = it }
    )
}

@Preview
@Composable
private fun EssentialInputSelectUsagePreview() {
    var value by remember { mutableStateOf("") }
    BuddyConTheme {
        EssentialInputSelectUsage(
            modifier = Modifier.fillMaxWidth(),
            title = "사용처",
            placeholder = "사용처 선택",
            value = value,
            onValueChange = { value = it }
        )
    }
}
