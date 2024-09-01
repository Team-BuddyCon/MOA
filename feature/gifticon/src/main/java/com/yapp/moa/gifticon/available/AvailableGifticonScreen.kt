package com.yapp.moa.gifticon.available

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.AsyncImage
import com.yapp.moa.designsystem.component.appbar.TopAppBarWithNotification
import com.yapp.moa.designsystem.component.appbar.getTopAppBarHeight
import com.yapp.moa.designsystem.component.button.CategoryStoreButton
import com.yapp.moa.designsystem.component.modal.FilterModalSheet
import com.yapp.moa.designsystem.component.modal.toOtherFormat
import com.yapp.moa.designsystem.component.tag.DDayTag
import com.yapp.moa.designsystem.component.tag.SortTag
import com.yapp.moa.designsystem.component.tooltips.MoaTooltip
import com.yapp.moa.designsystem.component.utils.SpacerHorizontal
import com.yapp.moa.designsystem.theme.BuddyConTheme
import com.yapp.moa.designsystem.theme.Grey60
import com.yapp.moa.designsystem.theme.Paddings
import com.yapp.moa.designsystem.theme.Pink100
import com.yapp.moa.designsystem.theme.White
import com.yapp.moa.domain.model.gifticon.AvailableGifticon
import com.yapp.moa.domain.model.type.GifticonStoreCategory
import com.yapp.moa.domain.model.type.SortType
import com.yapp.moa.gifticon.available.base.HandleDataResult
import com.yapp.moa.designsystem.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

private val TabHeight = 60.dp
private val TAG = "MOATest"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvailabeGifticonScreen(
    availableGifticonViewModel: AvailableGifticonViewModel = hiltViewModel(),
    showCoachMark: Boolean = false,
    onCloseCoachMark: () -> Unit = {},
    onNavigateToGifticonDetail: (Int) -> Unit,
    afterGifticonRegistrationCompletes: Boolean?
) {
    Log.e(TAG, "[AvailabeGifticonScreen] : ${availableGifticonViewModel.hashCode()}")

    val modalBottomSheetState = rememberModalBottomSheetState()
    var isBottomSheetOpen by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AvailabeGifticonContent(
            availableGifticonViewModel = availableGifticonViewModel,
            onFilterClicked = { isBottomSheetOpen = true },
            onGifticonItemClicked = { gifticonId ->
                onNavigateToGifticonDetail(gifticonId)
            },
            afterGifticonRegistrationCompletes = afterGifticonRegistrationCompletes
        )

        if (isBottomSheetOpen) {
            GifticonFilterBottomSheet(
                modalBottomSheetState = modalBottomSheetState,
                availableGifticonViewModel = availableGifticonViewModel,
                onDismiss = { isBottomSheetOpen = false }
            )
        }

        if (showCoachMark) {
            MoaTooltip(
                text = stringResource(R.string.gifticon_main_tooltip),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 82.dp)
                    .padding(end = 16.dp),
                onClose = onCloseCoachMark
            )
        }
    }
}

@Composable
private fun AvailabeGifticonContent(
    availableGifticonViewModel: AvailableGifticonViewModel,
    onFilterClicked: () -> Unit,
    onGifticonItemClicked: (Int) -> Unit,
    afterGifticonRegistrationCompletes: Boolean?
) {
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

    val lifeCycleOwner = LocalLifecycleOwner.current

    val currentAvailabeGifticons by availableGifticonViewModel.currentAvailableGifticons.collectAsStateWithLifecycle()

    val availableGifticonDetailState by availableGifticonViewModel.availableGifticonDetailState.collectAsStateWithLifecycle()
    val availableGifticonScreenUiState by availableGifticonViewModel.availableGifticonScreenUiState.collectAsStateWithLifecycle()
    val lazyGridState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        Log.e("MOAtest", "[AvailabeGifticonContent] - [LaunchedEffect(Unit)]")

        if (afterGifticonRegistrationCompletes == true) {
            availableGifticonViewModel.initPagingState()
            availableGifticonViewModel.getAvailableGifiticon()
        }
    }

    LaunchedEffect(Unit) {
        lifeCycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            availableGifticonViewModel.scrollToTopEvent.collectLatest {
                if (it) {
                    topAppBarOffsetHeightPx = 0f
                    lazyGridState.scrollToItem(0)
                }
            }
        }
    }

    LaunchedEffect(lazyGridState.canScrollForward) {
        if (lazyGridState.canScrollForward.not() && availableGifticonScreenUiState != AvailableGifticonScreenUiState.Loading) {
            availableGifticonViewModel.getAvailableGifiticon()
        }
    }

    HandleDataResult(
        dataResultStateFlow = availableGifticonViewModel.availableGifticonDataResult,
        onSuccess = {
            with(availableGifticonViewModel) {
                updateCurrentAvailabeGifticons(it.data)
                updateAvailableGifticonScreenUiState(AvailableGifticonScreenUiState.None)
                initAvailabeGifticonDataResult()
            }
        },
        onFailure = {
            // failure 처리 필요
            availableGifticonViewModel.updateAvailableGifticonScreenUiState(AvailableGifticonScreenUiState.Failure)
        },
        onLoading = {
            availableGifticonViewModel.updateAvailableGifticonScreenUiState(AvailableGifticonScreenUiState.Loading)
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(connection = nestedScrollConnection)
    ) {
        if (currentAvailabeGifticons.isNotEmpty()) {
            AvailableGifticons(
                lazyGridState = lazyGridState,
                topAppBarHeight = topAppBarHeight,
                currentAvailableGifticons = currentAvailabeGifticons,
                onGifticonItemClicked = { gifticonId ->
                    onGifticonItemClicked(gifticonId)
                }
            )
        } else {
            NoAvailableGifticonContent(topAppBarHeight = topAppBarHeight)
        }

        TopAppBarWithTab(
            topAppBarOffsetHeightPx = topAppBarOffsetHeightPx,
            currentStoreCategory = availableGifticonDetailState.currentStoreCategory,
            currentSortType = availableGifticonDetailState.currentSortType,
            onSelectedTabChanged = { newStoreCategory ->
                availableGifticonViewModel.updateCurrentStoreCategoryTab(newStoreCategory)
            },
            onFilterClicked = onFilterClicked
        )

        if (availableGifticonScreenUiState == AvailableGifticonScreenUiState.Loading) {
            LoadingStateScreen()
        }
    }
}

@Composable
private fun AvailableGifticons(
    lazyGridState: LazyGridState,
    topAppBarHeight: Dp,
    currentAvailableGifticons: List<AvailableGifticon.AvailableGifticonInfo>,
    onGifticonItemClicked: (Int) -> Unit
) {
    Log.e("MOATest", "AvailableGifticons")

    LazyVerticalGrid(
        state = lazyGridState,
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
                bottomPadding = if (index == currentAvailableGifticons.lastIndex) 56.dp else 0.dp,
                onGifticonItemClicked = { gifticonId ->
                    onGifticonItemClicked(gifticonId)
                }
            )
        }
    }
}

@Composable
private fun AvailableGifticonItem(
    availablieGifticonInfo: AvailableGifticon.AvailableGifticonInfo,
    topPadding: Dp,
    bottomPadding: Dp,
    onGifticonItemClicked: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(top = topPadding, bottom = bottomPadding)
            .clickable {
                onGifticonItemClicked(availablieGifticonInfo.gifticonId)
            }
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

            if (availablieGifticonInfo.expireDate.isNotEmpty()) {
                DDayTag(
                    modifier = Modifier
                        .padding(
                            top = Paddings.medium,
                            start = Paddings.medium
                        ),
                    dateMillis = SimpleDateFormat("yyyy-MM-dd").parse(availablieGifticonInfo.expireDate).time
                )
            }
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
            text = "~${availablieGifticonInfo.expireDate.toOtherFormat("yyyy.MM.dd")}",
            modifier = Modifier.padding(top = Paddings.medium),
            style = BuddyConTheme.typography.body03.copy(color = Grey60)
        )
    }
}

@Composable
private fun TopAppBarWithTab(
    topAppBarOffsetHeightPx: Float,
    currentStoreCategory: GifticonStoreCategory,
    currentSortType: SortType,
    onSelectedTabChanged: (newStoreCategory: GifticonStoreCategory) -> Unit,
    onFilterClicked: () -> Unit
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
                .padding(horizontal = Paddings.xlarge),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GifticonStoreCategoryTabUI(
                modifier = Modifier.weight(1f),
                currentStoreCategory = currentStoreCategory,
                onSelectedTabChanged = { onSelectedTabChanged(it) }
            )

            SpacerHorizontal(width = 30.dp)

            FilterUI(
                currentSortType = currentSortType,
                onFilterClicked = onFilterClicked
            )
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
private fun FilterUI(
    currentSortType: SortType,
    onFilterClicked: () -> Unit
) {
    SortTag(
        sortType = currentSortType,
        onAction = onFilterClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GifticonFilterBottomSheet(
    modalBottomSheetState: SheetState,
    availableGifticonViewModel: AvailableGifticonViewModel,
    onDismiss: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val availableGifticonDetailState by availableGifticonViewModel.availableGifticonDetailState.collectAsState()

    FilterModalSheet(
        sheetState = modalBottomSheetState,
        sortType = availableGifticonDetailState.currentSortType,
        onChangeSortType = { newSortType ->
            scope.launch {
                availableGifticonViewModel.updateCurrentSortType(newSortType = newSortType)
                modalBottomSheetState.hide()
                onDismiss()
            }
        },
        onDismiss = onDismiss
    )
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
            contentDescription = null
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

@Composable
fun LoadingStateScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Pink100
        )
    }
}
