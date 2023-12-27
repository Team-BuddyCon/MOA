package com.yapp.buddycon.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Grey50
import com.yapp.buddycon.domain.model.type.GifticonCategory

private val CategoryButtonHorizontalPadding = 12.dp
private val CategoryButtonVerticalPadding = 6.dp
private val CategoryButtonRadius = 20.dp

@Composable
fun CategoryButton(
    gifticonCategory: GifticonCategory,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .background(
                color = if (isSelected) BuddyConTheme.colors.primary else Color.Transparent,
                shape = RoundedCornerShape(CategoryButtonRadius)
            )
            .padding(
                horizontal = CategoryButtonHorizontalPadding,
                vertical = CategoryButtonVerticalPadding
            ).clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = gifticonCategory.value,
            style = BuddyConTheme.typography.body02.copy(
                color = if (isSelected) BuddyConTheme.colors.onPrimary else Grey50
            )
        )
    }
}

@Preview
@Composable
private fun CategoryButtonPreview() {
    BuddyConTheme {
        CategoryButton(
            gifticonCategory = GifticonCategory.ETC,
            isSelected = true
        )
    }
}
