package com.yapp.buddycon.designsystem.component.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.component.tag.DDayTag
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Grey40
import com.yapp.buddycon.designsystem.theme.Grey60
import com.yapp.buddycon.designsystem.theme.Paddings
import com.yapp.buddycon.designsystem.theme.Pink100
import com.yapp.buddycon.domain.model.gifticon.AvailableGifticon
import com.yapp.buddycon.domain.model.type.GifticonStore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val GifticonInfoListModalSheetHeight = 573.dp
private val GifticonInfoListModalSheetRadius = 24.dp
private val GifticonInfoListModalSheetItemElevation = 10.dp
private val GifticonInfoListModalSheetItemRadius = 8.dp
private const val DATE_FORMAT = "yyyy-MM-dd"

@Suppress("SimpleDateFormat")
fun String.toDate(): Date = SimpleDateFormat(DATE_FORMAT, Locale.KOREA).parse(this) ?: Date()

fun String.toOtherFormat(pattern: String): String {
    val date = SimpleDateFormat(DATE_FORMAT, Locale.KOREA).parse(this) ?: Date()
    return SimpleDateFormat(pattern, Locale.KOREA).format(date)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GifticonInfoListModalSheet(
    modifier: Modifier = Modifier,
    countOfUsableGifticon: Int,
    countOfImminetGifticon: Int,
    gifticonInfos: LazyPagingItems<AvailableGifticon.AvailableGifticonInfo>,
    gifticonStore: GifticonStore
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Paddings.xlarge)
    ) {
        Spacer(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = Paddings.xlarge)
                .size(32.dp, 4.dp)
                .background(Grey40, RoundedCornerShape(100.dp))
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
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
            if (gifticonStore !in arrayOf(GifticonStore.TOTAL, GifticonStore.OTHERS, GifticonStore.NONE)) {
                Spacer(Modifier.weight(1f))
                Icon(
                    painter = painterResource(gifticonStore.logo()),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = Color.Unspecified
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = Paddings.xlarge),
            verticalArrangement = Arrangement.spacedBy(Paddings.xlarge),
            horizontalArrangement = Arrangement.spacedBy(Paddings.medium)
        ) {
            items(gifticonInfos.itemCount) { index ->
                gifticonInfos[index]?.let { gifticonInfo ->
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                        ) {
                            Card(
                                elevation = CardDefaults.cardElevation(defaultElevation = GifticonInfoListModalSheetItemElevation),
                                shape = RoundedCornerShape(GifticonInfoListModalSheetItemRadius)
                            ) {
                                AsyncImage(
                                    model = gifticonInfo.imageUrl,
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.FillBounds
                                )
                            }
                            DDayTag(
                                modifier = Modifier
                                    .padding(
                                        top = Paddings.medium,
                                        start = Paddings.medium
                                    ),
                                dateMillis = gifticonInfo.expireDate.toDate().time
                            )
                        }
                        Text(
                            text = gifticonInfo.category.value,
                            modifier = Modifier.padding(top = Paddings.large),
                            style = BuddyConTheme.typography.body03.copy(color = Pink100),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        Text(
                            text = gifticonInfo.name,
                            style = BuddyConTheme.typography.body04,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        Text(
                            text = "~${gifticonInfo.expireDate.toOtherFormat("yyyy.MM.dd")}",
                            modifier = Modifier.padding(top = Paddings.medium),
                            style = BuddyConTheme.typography.body03.copy(color = Grey60)
                        )
                    }
                }
            }
        }
    }
}
