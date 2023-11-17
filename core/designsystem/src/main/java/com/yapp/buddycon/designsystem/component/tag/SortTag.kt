package com.yapp.buddycon.designsystem.component.tag

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Grey40
import com.yapp.buddycon.designsystem.theme.Paddings
import com.yapp.buddycon.domain.model.type.SortType

private val SORT_ICON_SIZE = 18.dp

@Composable
fun SortTag(
    sortType: SortType,
    onAction: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .clickable { onAction() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = sortType.value,
            style = BuddyConTheme.typography.body02.copy(color = Grey40)
        )
        Spacer(Modifier.padding(Paddings.small))
        Icon(
            painter = painterResource(R.drawable.ic_down_arrow),
            contentDescription = sortType.value,
            modifier = Modifier.size(SORT_ICON_SIZE),
            tint = Grey40
        )
    }
}

@Preview
@Composable
private fun SortTagPreview() {
    BuddyConTheme {
        SortTag(
            sortType = SortType.EXPIRATION_DATE
        )
    }
}
