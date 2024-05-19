package com.yapp.buddycon.mypage.usedgifticon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.component.appbar.TopAppBarWithBack
import com.yapp.buddycon.designsystem.component.modal.toOtherFormat
import com.yapp.buddycon.designsystem.component.utils.SpacerVertical
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Grey60
import com.yapp.buddycon.designsystem.theme.Paddings
import com.yapp.buddycon.designsystem.theme.Pink100
import com.yapp.buddycon.domain.model.gifticon.UnavailableGifticon

@Composable
fun UsedGifticon(
    onBack: () -> Unit,
    unavailableGifticonViewModel: UnavailableGifticonViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBarWithBack(
                title = stringResource(R.string.used_gifticon_screen_appbar_title),
                onBack = onBack
            )
        }
    ) { paddingValues ->
        UsedGifticonContent(
            modifier = Modifier.padding(paddingValues = paddingValues),
            unavailableGifticonViewModel = unavailableGifticonViewModel
        )
    }
}

@Composable
private fun UsedGifticonContent(
    modifier: Modifier = Modifier,
    unavailableGifticonViewModel: UnavailableGifticonViewModel
) {
    val usedGifticonCount by unavailableGifticonViewModel.unAvailableGifticonCount.collectAsStateWithLifecycle()
    val unAvailableGifticons = unavailableGifticonViewModel.unAvailableGifticonPagingItems.collectAsLazyPagingItems()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        SpacerVertical(height = 24.dp)
        UsedGiftionCount(usedGifticonCount)
        UsedGifticons(
            unAvailableGifticons = unAvailableGifticons
        )
    }
}

@Composable
private fun UsedGiftionCount(count: Int) {
    Text(
        text = "${count}개 사용했어요!",
        style = BuddyConTheme.typography.title02
    )
}

@Composable
private fun UsedGifticons(
    unAvailableGifticons: LazyPagingItems<UnavailableGifticon.UnavailableGifticonInfo>,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = Paddings.xlarge),
        verticalArrangement = Arrangement.spacedBy(Paddings.xlarge),
        horizontalArrangement = Arrangement.spacedBy(Paddings.medium)
    ) {
        items(unAvailableGifticons.itemCount) { index ->
            unAvailableGifticons[index]?.let { gifticonInfo ->
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    ) {
                        Card(
                            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            AsyncImage(
                                model = gifticonInfo.imageUrl,
                                contentDescription = gifticonInfo.name,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.FillBounds,
                                colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
                            )
                        }
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
