package com.yapp.buddycon.designsystem.component.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Grey40
import com.yapp.buddycon.designsystem.theme.Paddings
import com.yapp.buddycon.designsystem.theme.Pink100

private val GifticonInfoModalSheetHeight = 167.dp
private val GifticonInfoModalSheetRadius = 24.dp
private val GifticonInfoModalHorizontalPadding = 15.dp
private val GifticonInfoModalItemIconSize = 64.dp
private const val GifticonInfoModalDescription = "GifticonInfoModal"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GifticonInfoModalSheetContent(
    modifier: Modifier = Modifier,
    countOfUsableGifticon: Int,
    countOfImminetGifticon: Int
) {
    Column(
        modifier = modifier
            .background(BuddyConTheme.colors.background)
            .fillMaxWidth()
            .padding(horizontal = GifticonInfoModalHorizontalPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .padding(vertical = Paddings.xlarge)
                .size(32.dp, 4.dp)
                .background(Grey40, RoundedCornerShape(100.dp))
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.modal_sheet_gifticon),
                    style = BuddyConTheme.typography.subTitle
                )
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "${countOfUsableGifticon}개",
                        style = BuddyConTheme.typography.title01
                    )
                    Text(
                        text = String.format(
                            stringResource(R.string.modal_sheet_imminet_gifticon),
                            countOfImminetGifticon
                        ),
                        modifier = Modifier.padding(start = Paddings.medium, bottom = Paddings.small),
                        style = BuddyConTheme.typography.body04.copy(color = Pink100)
                    )
                }
            }
            Icon(
                painter = painterResource(R.drawable.ic_gifticon),
                contentDescription = GifticonInfoModalDescription,
                modifier = Modifier.size(GifticonInfoModalItemIconSize),
                tint = Color.Unspecified
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun GifticonInfoModalSheetPreview() {
    BuddyConTheme {
        GifticonInfoModalSheetContent(
            countOfUsableGifticon = 12,
            countOfImminetGifticon = 1
        )
    }
}
