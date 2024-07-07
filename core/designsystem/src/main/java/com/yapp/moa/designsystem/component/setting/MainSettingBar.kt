package com.yapp.moa.designsystem.component.setting

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.yapp.moa.designsystem.component.utils.SpacerHorizontal
import com.yapp.moa.designsystem.theme.BuddyConTheme
import com.yapp.moa.designsystem.theme.Grey40
import com.yapp.moa.designsystem.theme.Grey60
import com.yapp.moa.designsystem.R

@Composable
fun MainSettingBar(
    mainTitle: String,
    subText: String? = null,
    @DrawableRes rightIconResource: Int = R.drawable.ic_right_arrow,
    onSettingClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSettingClick() }
            .padding(horizontal = 16.dp, vertical = 17.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = mainTitle,
            style = BuddyConTheme.typography.subTitle,
            modifier = Modifier.weight(1.0f),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

        subText?.let {
            Text(
                text = it,
                style = BuddyConTheme.typography.body03,
                color = Grey60,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            SpacerHorizontal(width = 8.dp)
        }

        Icon(
            painter = painterResource(id = rightIconResource),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Grey40
        )
    }
}
