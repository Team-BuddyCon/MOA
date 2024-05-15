package com.yapp.buddycon.map

import android.content.Context
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.label.Label
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.component.appbar.TopAppBarWithNotification
import com.yapp.buddycon.designsystem.component.button.CategoryButton
import com.yapp.buddycon.designsystem.component.modal.GifticonInfoListModalSheet
import com.yapp.buddycon.designsystem.component.modal.GifticonInfoModalSheetContent
import com.yapp.buddycon.designsystem.component.modal.PlaceModalSheet
import com.yapp.buddycon.designsystem.component.snackbar.BuddyConSnackbar
import com.yapp.buddycon.designsystem.component.snackbar.showBuddyConSnackBar
import com.yapp.buddycon.designsystem.theme.Black
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Grey70
import com.yapp.buddycon.designsystem.theme.Paddings
import com.yapp.buddycon.designsystem.theme.Pink100
import com.yapp.buddycon.domain.model.kakao.SearchPlaceModel
import com.yapp.buddycon.domain.model.type.GifticonStore
import com.yapp.buddycon.utility.RequestLocationPermission
import com.yapp.buddycon.utility.checkLocationPermission
import com.yapp.buddycon.utility.getCurrentLocation
import com.yapp.buddycon.utility.getLocationLabels
import com.yapp.buddycon.utility.isDeadLine
import com.yapp.buddycon.utility.scaleToLabel
import timber.log.Timber

// TopAppBarHeight(52) + BottomNavigationBarHeight(72) + MapCategoryTabHeight(60)
private const val MapBarSize = 184f

@Stable
data class MapLocation(
    val location: Location? = null
)

@Stable
data class MapSearchPlace(
    val searchPlaceModels: List<SearchPlaceModel> = listOf()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    mapViewModel: MapViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var currentLocation by remember { mutableStateOf(MapLocation()) }
    var isGrantedPermission by remember { mutableStateOf(checkLocationPermission(context)) }
    var isShowToast by remember { mutableStateOf(false) }

    val configuration = LocalConfiguration.current
    val mapHeightDp = configuration.screenHeightDp.toFloat() - MapBarSize
    val heightDp by mapViewModel.heightDp.collectAsStateWithLifecycle()
    val store by mapViewModel.store.collectAsStateWithLifecycle()
    val mapSearchPlace by mapViewModel.mapSearchPlace.collectAsStateWithLifecycle()
    val gifticonExistStore by mapViewModel.gifticonExistStore.collectAsStateWithLifecycle()

    // 위치 기반 지도 검색
    LaunchedEffect(currentLocation, store, gifticonExistStore) {
        currentLocation.location?.let { location ->
            when (store) {
                GifticonStore.TOTAL -> {
                    mapViewModel.searchPlacesByKeyword(
                        stores = gifticonExistStore,
                        x = location.longitude.toString(),
                        y = location.latitude.toString()
                    )
                }

                else -> {
                    mapViewModel.searchPlacesByKeyword(
                        stores = listOf(store),
                        x = location.longitude.toString(),
                        y = location.latitude.toString()
                    )
                }
            }
        }
    }

    // 위치 권한 있을 경우 현재 위치 확인
    LaunchedEffect(isGrantedPermission) {
        if (isGrantedPermission) {
            getCurrentLocation(context = context) {
                currentLocation = currentLocation.copy(location = it)
            }
        }
    }

    // 최초 시스템 위치 권한 요청
    RequestLocationPermission(
        onGranted = {
            isGrantedPermission = checkLocationPermission(context)
            isShowToast = true
            showBuddyConSnackBar(
                message = context.getString(R.string.map_location_permission),
                scope = coroutineScope,
                snackbarHostState = snackbarHostState
            ) {
                isShowToast = false
            }
        }
    )
    BottomSheetScaffold(
        sheetContent = {
            MapBottomSheet(
                mapViewModel = mapViewModel,
                mapHeightDp = mapHeightDp
            )
        },
        sheetPeekHeight = heightDp.dp,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetContainerColor = BuddyConTheme.colors.background,
        sheetShadowElevation = 33.dp,
        topBar = {
            TopAppBarWithNotification(
                title = stringResource(R.string.map)
            )
        },
        snackbarHost = {
            BuddyConSnackbar(
                modifier = Modifier.padding(top = 162.dp),
                snackbarHostState = snackbarHostState,
                contentAlignment = Alignment.TopCenter
            )
        },
        sheetDragHandle = null
    ) {
        MapContent(
            modifier = Modifier
                .fillMaxSize()
                .background(BuddyConTheme.colors.background),
            mapLocation = currentLocation,
            mapSearchPlace = mapSearchPlace,
            isGranted = isGrantedPermission,
            isShowToast = isShowToast
        )
    }
}

@Composable
private fun MapBottomSheet(
    modifier: Modifier = Modifier,
    mapViewModel: MapViewModel = hiltViewModel(),
    mapHeightDp: Float
) {
    val context = LocalContext.current
    val density = context.resources.displayMetrics.density
    val heightDp by mapViewModel.heightDp.collectAsStateWithLifecycle()
    val sheetValue by mapViewModel.sheetValue.collectAsStateWithLifecycle()
    val offset by mapViewModel.offset.collectAsStateWithLifecycle()
    val totalCount by mapViewModel.totalCount.collectAsStateWithLifecycle()
    val gifticonInfos = mapViewModel.gifticonPagingItems.collectAsLazyPagingItems()
    val gifticonStore by mapViewModel.store.collectAsStateWithLifecycle()
    val deadLineCount by mapViewModel.deadLineCount.collectAsStateWithLifecycle()

    LaunchedEffect(gifticonInfos.itemCount) {
        mapViewModel.setGifticonItemsInfo(
            deadLineCount = (0 until gifticonInfos.itemCount)
                .mapNotNull { gifticonInfos[it] }
                .count { it.expireDate.isDeadLine() },
            gifticonStores = (0 until gifticonInfos.itemCount)
                .mapNotNull { gifticonInfos[it]?.category }
                .toSet()
                .toList()
        )
    }

    Box(
        modifier = modifier
            .heightIn(min = BottomSheetValue.Collapsed.sheetPeekHeightDp.dp, max = BottomSheetValue.Expanded.sheetPeekHeightDp.dp)
            .fillMaxSize()
            .background(BuddyConTheme.colors.background)
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onDragEnd = {
                        mapViewModel.changeBottomSheetValue(
                            transitionBottomSheet(
                                current = sheetValue,
                                offset = offset
                            )
                        )
                    },
                    onVerticalDrag = { _, dragAmount ->
                        if (heightDp - (dragAmount / density) in 0f..mapHeightDp) {
                            mapViewModel.setOffset(dragAmount / density)
                        }
                    }
                )
            }
    ) {
        when (sheetValue) {
            BottomSheetValue.Expanded -> {
                // TODO totalCount API 반영 및 유효기간
                GifticonInfoListModalSheet(
                    countOfUsableGifticon = totalCount,
                    countOfImminetGifticon = deadLineCount,
                    gifticonInfos = gifticonInfos,
                    gifticonStore = gifticonStore
                )
            }

            else -> {
                GifticonInfoModalSheetContent(
                    countOfUsableGifticon = totalCount,
                    countOfImminetGifticon = deadLineCount
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MapContent(
    modifier: Modifier = Modifier,
    mapViewModel: MapViewModel = hiltViewModel(),
    mapLocation: MapLocation,
    mapSearchPlace: MapSearchPlace,
    isGranted: Boolean,
    isShowToast: Boolean = false
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val store by mapViewModel.store.collectAsStateWithLifecycle()
    val placeLabels by mapViewModel.placeLabels.collectAsStateWithLifecycle()
    var map by remember { mutableStateOf<KakaoMap?>(null) }
    var clickedLabel by remember { mutableStateOf<Label?>(null) }

    LaunchedEffect(mapSearchPlace) {
        // 지도가 노출되고 나서 라벨 표시, 라벨과 함께 표시하게되면 mapReady 오랜 시간 소요
        map?.labelManager?.clearAll()
        map?.labelManager?.let { manager ->
            mapViewModel.setPlaceLabels(
                getLocationLabels(
                    labelManager = manager,
                    searchPlaceModels = mapSearchPlace.searchPlaceModels
                )
            )
        }
        map?.setOnLabelClickListener { kakaoMap, layer, label ->
            clickedLabel = label
            scaleToLabel(label, 1.5f)
        }
    }

    if (clickedLabel != null) {
        placeLabels.entries.find { it.value == clickedLabel }?.key?.let { model ->
            PlaceModalSheet(
                searchPlaceModel = model,
                onDismiss = {
                    clickedLabel?.let { label ->
                        scaleToLabel(label, 1f)
                    }
                    clickedLabel = null
                }
            )
        }
    }

    Column(modifier) {
        MapCategoryTab(
            category = store,
            onCategoryChange = { mapViewModel.selectGifticonStore(it) }
        )
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    MapView(context)
                },
                update = {
                    it.start(
                        object : MapLifeCycleCallback() {
                            override fun onMapDestroy() {
                            }

                            override fun onMapError(error: Exception?) {
                                Timber.e("onMapError ${error?.message}")
                            }
                        },
                        object : KakaoMapReadyCallback() {
                            override fun onMapReady(kakaoMap: KakaoMap) {
                                map = kakaoMap
                            }

                            override fun getPosition(): LatLng {
                                mapLocation.location?.let { location ->
                                    return LatLng.from(location.latitude, location.longitude)
                                } ?: kotlin.run {
                                    return super.getPosition()
                                }
                            }
                        }
                    )
                }
            )

            if (isGranted.not()) {
                Spacer(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Black.copy(0.4f))
                )
                Card(
                    modifier = Modifier
                        .padding(top = Paddings.xlarge)
                        .align(Alignment.TopCenter)
                        .height(45.dp),
                    shape = RoundedCornerShape((22.5).dp),
                    colors = CardDefaults.cardColors(
                        containerColor = BuddyConTheme.colors.background
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(horizontal = (17.5).dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.location_permission_guide_message),
                            style = BuddyConTheme.typography.body04.copy(
                                color = Grey70
                            )
                        )
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(Pink100)) {
                                    append(stringResource(R.string.location_permission_move_setting))
                                }
                            },
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .clickable { moveToSetting(context) },
                            style = BuddyConTheme.typography.body04
                        )
                    }
                }
            } else {
                if (!isShowToast) {
                    Card(
                        modifier = Modifier
                            .padding(top = Paddings.xlarge)
                            .align(Alignment.TopCenter)
                            .height(45.dp),
                        shape = RoundedCornerShape((22.5).dp),
                        colors = CardDefaults.cardColors(
                            containerColor = BuddyConTheme.colors.background
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(horizontal = (17.5).dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    append("상단 탭을 눌러 ")
                                    withStyle(style = SpanStyle(Pink100)) {
                                        append("주변에 있는 매장")
                                    }
                                    append("을 확인하세요.")
                                },
                                style = BuddyConTheme.typography.body04.copy(
                                    color = Grey70
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MapCategoryTab(
    category: GifticonStore,
    onCategoryChange: (GifticonStore) -> Unit = { }
) {
    LazyRow(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .padding(horizontal = Paddings.xlarge)
            .padding(top = Paddings.xlarge, bottom = Paddings.large),
        horizontalArrangement = Arrangement.spacedBy(Paddings.small)
    ) {
        items(GifticonStore.values()) {
            CategoryButton(
                gifticonCategory = it,
                isSelected = it == category,
                onClick = { onCategoryChange(it) }
            )
        }
    }
}

private fun transitionBottomSheet(current: BottomSheetValue, offset: Float): BottomSheetValue {
    return if (offset > 0f) {
        when (current) {
            BottomSheetValue.Collapsed -> BottomSheetValue.PartiallyExpanded
            BottomSheetValue.PartiallyExpanded -> BottomSheetValue.Expanded
            BottomSheetValue.Expanded -> BottomSheetValue.Expanded
        }
    } else {
        when (current) {
            BottomSheetValue.Collapsed -> BottomSheetValue.Collapsed
            BottomSheetValue.PartiallyExpanded -> BottomSheetValue.Collapsed
            BottomSheetValue.Expanded -> BottomSheetValue.PartiallyExpanded
        }
    }
}

private fun moveToSetting(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = Uri.parse("package:${context.packageName}")
    context.startActivity(intent)
}
