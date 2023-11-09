package com.yapp.buddycon.designsystem.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Grey50
import com.yapp.buddycon.designsystem.theme.Pink100
import com.yapp.buddycon.designsystem.theme.White


@Composable
fun SelectedCategoryButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    BasicCategoryButton(text = text, onClick = onClick, isSelected = true, modifier = modifier)
}

@Composable
fun UnSelectedCategoryButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    BasicCategoryButton(text = text, onClick = onClick, isSelected = false, modifier = modifier)
}

@Composable
fun BasicCategoryButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isSelected: Boolean,
) {
    val textColor: Color = if (isSelected) White else Grey50
    val textBG: Color = if (isSelected) Pink100 else White.copy(alpha = 0.0f)
    Button(
        onClick = { onClick() },
        modifier = modifier.height(32.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = textBG
        ),
        contentPadding = PaddingValues(12.dp, 6.dp)
    ) {
        Text(
            text = text, style = BuddyConTheme.typography.body02, color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SelectedCategoryButton() {
    BuddyConTheme {
        SelectedCategoryButton(text = "전체") {}
    }
}

@Preview(showBackground = false)
@Composable
fun UnSelectedCategoryButton() {
    BuddyConTheme {
        UnSelectedCategoryButton(text = "스타벅스") {}
    }
}


