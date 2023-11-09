package com.yapp.buddycon.designsystem.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Grey30
import com.yapp.buddycon.designsystem.theme.Grey40
import com.yapp.buddycon.designsystem.theme.Grey60
import com.yapp.buddycon.designsystem.theme.Pink100

@Composable
fun MainButton(
    text: String,
    modifier: Modifier = Modifier,
    bgColors: Color = Pink100,
    onClick: () -> Unit,
) {
    val textColor: Color = if (bgColors == Grey30) Grey60 else White
    BuddyConBasicButton(
        onClick = onClick,
        buttonText = text,
        modifier = modifier,
        buttonBackgroundColor = bgColors,
        buttonTextColor = textColor
    )
}

@Composable
fun DisabledButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    BuddyConBasicButton(
        onClick = { onClick },
        buttonBackgroundColor = Grey40,
        buttonText = text,
        buttonTextColor = Grey60,
        modifier = modifier
    )
}

@Composable
fun BuddyConBasicButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    buttonBackgroundColor: Color,
    buttonTextColor: Color = White,
    buttonText: String = stringResource(id = R.string.button_default_name),
) {
    Button(
        onClick = { onClick() },
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(
            containerColor = buttonBackgroundColor
        ), contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        Text(
            text = buttonText, style = BuddyConTheme.typography.subTitle, color = buttonTextColor
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun MainPinkButtonPreview() {
    BuddyConTheme {
        MainButton(text = "버튼") {}
    }
}

@Preview(showBackground = true)
@Composable
private fun GreyButtonPreview() {
    BuddyConTheme {
        MainButton(text = "버튼", bgColors = Grey30) {}
    }
}

@Preview(showBackground = true)
@Composable
private fun DisabledButtonPreview() {
    BuddyConTheme {
        DisabledButton(text = "버튼") {}
    }
}