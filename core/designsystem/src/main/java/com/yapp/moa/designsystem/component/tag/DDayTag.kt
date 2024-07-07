package com.yapp.moa.designsystem.component.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.moa.designsystem.theme.BuddyConTheme
import com.yapp.moa.designsystem.theme.Grey60
import com.yapp.moa.designsystem.theme.Grey90
import com.yapp.moa.designsystem.theme.White
import com.yapp.moa.designsystem.R
import java.util.Calendar

private const val MILLIS_OF_DAY = 1000 * 60 * 60 * 24L

enum class DDayType {
    EXPIRATION, D_DAY, D_9, D_99, D_365, D_365_MORE
}

@Composable
fun DDayTag(
    modifier: Modifier = Modifier,
    dateMillis: Long
) {
    val today = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time.time
    val offset = (dateMillis - today) / MILLIS_OF_DAY
    val ddayType = getDDayType(offset = offset)

    Box(
        modifier = modifier
            .background(
                color = when (ddayType) {
                    DDayType.EXPIRATION -> Grey90
                    DDayType.D_DAY, DDayType.D_9 -> BuddyConTheme.colors.primary
                    else -> Grey60
                },
                shape = RoundedCornerShape(100.dp)
            )
            .padding(horizontal = 12.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = when (ddayType) {
                DDayType.EXPIRATION -> stringResource(R.string.tag_expiration)
                DDayType.D_DAY -> stringResource(R.string.tag_dday)
                DDayType.D_9 -> String.format(stringResource(R.string.tag_d_9_format), offset)
                DDayType.D_99 -> String.format(stringResource(R.string.tag_d_99_format), offset)
                DDayType.D_365 -> String.format(stringResource(R.string.tag_d_365_format), offset)
                DDayType.D_365_MORE -> String.format(stringResource(R.string.tag_d_365_more_format), offset)
            },
            style = BuddyConTheme.typography.body04.copy(color = White)
        )
    }
}

private fun getDDayType(offset: Long) = when {
    offset < 0 -> DDayType.EXPIRATION
    offset == 0L -> DDayType.D_DAY
    offset in (1 until 10) -> DDayType.D_9
    offset in (10 until 100) -> DDayType.D_99
    offset in (100..365) -> DDayType.D_365
    else -> DDayType.D_365_MORE
}

@Preview
@Composable
private fun DDayTagPreview() {
    val today = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time.time
    BuddyConTheme {
        DDayTag(dateMillis = today + (MILLIS_OF_DAY * 1))
    }
}
