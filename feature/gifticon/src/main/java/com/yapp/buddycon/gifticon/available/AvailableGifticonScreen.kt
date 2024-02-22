package com.yapp.buddycon.gifticon.available

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.component.appbar.TopAppBarWithNotification
import com.yapp.buddycon.designsystem.component.appbar.getTopAppBarHeight
import com.yapp.buddycon.designsystem.component.button.CategoryStoreButton
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Grey60
import com.yapp.buddycon.designsystem.theme.Paddings
import com.yapp.buddycon.designsystem.theme.Pink100
import com.yapp.buddycon.designsystem.theme.White
import com.yapp.buddycon.domain.model.gifticon.AvailableGifticon
import com.yapp.buddycon.domain.model.type.GifticonStoreCategory
import com.yapp.buddycon.gifticon.available.base.HandleDataResult
import kotlin.math.roundToInt

private val TabHeight = 60.dp
private val TAG = "BuddyConTest"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvailabeGifticonScreen(
    availableGifticonViewModel: AvailableGifticonViewModel = hiltViewModel()
) {
    Log.e(TAG, "[AvailabeGifticonScreen] : ${availableGifticonViewModel.hashCode()}")

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        sheetContent = {
            GifticonFilterBottomSheet()
        },
        sheetPeekHeight = 0.dp
    ) {
        AvailabeGifticonContent(availableGifticonViewModel)
    }
}

@Composable
private fun AvailabeGifticonContent(availableGifticonViewModel: AvailableGifticonViewModel) {
    val topAppBarHeight = getTopAppBarHeight()
    val topAppBarHeightPx = with(LocalDensity.current) { topAppBarHeight.roundToPx().toFloat() }

    var topAppBarOffsetHeightPx by remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = topAppBarOffsetHeightPx + delta
                topAppBarOffsetHeightPx = newOffset.coerceIn(-topAppBarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }

    val currentStoreCategory by availableGifticonViewModel.currentStoreCategoryTab.collectAsState()
    val currentAvailabeGifticons by availableGifticonViewModel.currentAvailableGifticons.collectAsState()

    LaunchedEffect(Unit) {
        availableGifticonViewModel.getAvailableGifiticon()
    }

    HandleDataResult(
        dataResultStateFlow = availableGifticonViewModel.availableGifticonDataResult,
        onSuccess = {
            Log.e(TAG, "[HandleDataResult] - onSuccess : ${it.data}")
            availableGifticonViewModel.updateCurrentAvailabeGifticons(it.data)
        },
        onFailure = {
            Log.e(TAG, "[HandleDataResult] - onFailure : ${it.throwable}") // failure 처리 필요
        },
        onLoading = {
            Log.e(TAG, "[HandleDataResult] - onLoading") // loading ui ?
        }
    )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(connection = nestedScrollConnection)
    ) {
        if (currentAvailabeGifticons.isNotEmpty()) {
            AvailableGifticons(
                topAppBarHeight = topAppBarHeight,
                currentAvailableGifticons = currentAvailabeGifticons
            )
        } else {
            NoAvailableGifticonContent(topAppBarHeight = topAppBarHeight)
        }

        TopAppBarWithTab(
            topAppBarOffsetHeightPx = topAppBarOffsetHeightPx,
            currentStoreCategory = currentStoreCategory,
            onSelectedTabChanged = { newStoreCategory ->
                availableGifticonViewModel.updateCurrentStoreCategoryTab(newStoreCategory)
            }
        )
    }
}

@Composable
private fun AvailableGifticons(
    topAppBarHeight: Dp,
    currentAvailableGifticons: List<AvailableGifticon.AvailableGifticonInfo>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(top = topAppBarHeight + TabHeight),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        itemsIndexed(
            items = currentAvailableGifticons,
            key = { index, item -> "$index ${item.gifticonId} ${item.imageUrl}" }
        ) { index, availablieGifticonInfo ->
            AvailableGifticonItem(
                availablieGifticonInfo = availablieGifticonInfo,
                topPadding = 16.dp,
                bottomPadding = if (index == currentAvailableGifticons.lastIndex) 56.dp else 0.dp
            )
        }
    }
}

@Composable
private fun AvailableGifticonItem(
    availablieGifticonInfo: AvailableGifticon.AvailableGifticonInfo,
    topPadding: Dp,
    bottomPadding: Dp
) {
    Column(
        modifier = Modifier.padding(top = topPadding, bottom = bottomPadding)
    ) {
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
                    model = availablieGifticonInfo.imageUrl,
                    contentDescription = availablieGifticonInfo.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
//  날짜 로직 추가 후 완성 예정
//            DDayTag(
//                modifier = Modifier
//                    .padding(
//                        top = Paddings.medium,
//                        start = Paddings.medium
//                    ),
//                dateMillis = availablieGifticonInfo.
//            )
        }
        Text(
            text = availablieGifticonInfo.category.value,
            modifier = Modifier.padding(top = Paddings.large),
            style = BuddyConTheme.typography.body03.copy(color = Pink100),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Text(
            text = availablieGifticonInfo.name,
            style = BuddyConTheme.typography.body04,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Text(
            text = "~${availablieGifticonInfo.expireDate}", // 날짜 표출 형식 수정 필요
            modifier = Modifier.padding(top = Paddings.medium),
            style = BuddyConTheme.typography.body03.copy(color = Grey60)
        )
    }
}

@Composable
private fun TopAppBarWithTab(
    topAppBarOffsetHeightPx: Float,
    currentStoreCategory: GifticonStoreCategory,
    onSelectedTabChanged: (newStoreCategory: GifticonStoreCategory) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .offset {
                IntOffset(
                    x = 0,
                    y = topAppBarOffsetHeightPx.roundToInt()
                )
            }
            .background(White)
    ) {
        HomeTopAppBar()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Paddings.xlarge)
        ) {
            GifticonStoreCategoryTabUI(
                modifier = Modifier.weight(1f),
                currentStoreCategory = currentStoreCategory,
                onSelectedTabChanged = { onSelectedTabChanged(it) }
            )

            // Todo
            FilterUI()
        }
    }
}

@Composable
private fun HomeTopAppBar() {
    TopAppBarWithNotification(
        title = stringResource(R.string.gifticon)
    )
}

@Composable
private fun GifticonStoreCategoryTabUI(
    modifier: Modifier,
    currentStoreCategory: GifticonStoreCategory,
    onSelectedTabChanged: (newStoreCategory: GifticonStoreCategory) -> Unit
) {
    Row(
        modifier = modifier.height(60.dp)
    ) {
        LazyRow(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .padding(top = Paddings.xlarge, bottom = Paddings.large),
            horizontalArrangement = Arrangement.spacedBy(Paddings.small)
        ) {
            items(GifticonStoreCategory.values()) {
                CategoryStoreButton(
                    gifticonStoreCategory = it,
                    isSelected = it == currentStoreCategory,
                    onClick = { onSelectedTabChanged(it) }
                )
            }
        }
    }
}

@Composable
private fun FilterUI() {
    // Todo : BottomSheet
}

@Composable
private fun GifticonFilterBottomSheet() {
    // Todo : BottomSheet
}

@Composable
private fun NoAvailableGifticonContent(topAppBarHeight: Dp) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = topAppBarHeight + TabHeight),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            painter = painterResource(R.drawable.img_empty_box),
            contentDescription = null,
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = BuddyConTheme.typography.body03,
            text = buildAnnotatedString {
                append("아래 ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("플러스 버튼")
                }
                append("을 눌러\n가지고 있는 기프티콘을 등록해 주세요")
            }
        )
    }
}


