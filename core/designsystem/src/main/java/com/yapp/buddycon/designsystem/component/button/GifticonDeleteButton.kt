package com.yapp.buddycon.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.component.utils.SpacerHorizontal
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Grey20
import com.yapp.buddycon.designsystem.theme.Grey50

private val GifticonDeleteButtonRadius = 8.dp

@Composable
fun GifticonDeleteButton(
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .background(
                color = Grey20,
                shape = RoundedCornerShape(GifticonDeleteButtonRadius)
            )
            .padding(
                horizontal = 10.dp,
                vertical = 6.dp
            ).clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_remove),
            contentDescription = "delete",
            modifier = Modifier
                .size(24.dp),
            tint = Grey50
        )

        SpacerHorizontal(width = 4.dp)

        Text(
            text = stringResource(R.string.gifticon_delete),
            style = BuddyConTheme.typography.body02.copy(
                color = Grey50
            )
        )
    }
}
