package com.yapp.moa.designsystem.component.tooltips

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.yapp.moa.designsystem.theme.Black
import com.yapp.moa.designsystem.theme.BuddyConTheme
import com.yapp.moa.designsystem.theme.White
import com.yapp.moa.designsystem.R

@Composable
fun MoaTooltip(
    modifier: Modifier = Modifier,
    text: String,
    onClose: () -> Unit = {}
) {
    Column(
        modifier = modifier.size(291.dp, 57.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Black.copy(0.4f), RoundedCornerShape(24.dp))
                .padding(horizontal = 20.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                modifier = Modifier.weight(1f),
                style = BuddyConTheme.typography.body03.copy(color = White)
            )
            Icon(
                painter = painterResource(R.drawable.ic_close),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onClose() },
                tint = White
            )
        }
        Canvas(
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 25.dp)
        ) {
            val path = Path()
            path.moveTo(0.dp.toPx(), 0.dp.toPx())
            path.lineTo((-6).dp.toPx(), 8.dp.toPx())
            path.lineTo((-12).dp.toPx(), 0.dp.toPx())
            path.close()
            drawPath(path, Black.copy(0.4f))
        }
    }
}
