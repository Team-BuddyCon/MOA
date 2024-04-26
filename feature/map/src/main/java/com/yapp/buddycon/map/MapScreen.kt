package com.yapp.buddycon.map

import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.component.appbar.TopAppBarWithNotification
import com.yapp.buddycon.designsystem.component.button.CategoryButton
import com.yapp.buddycon.designsystem.component.dialog.DefaultDialog
import com.yapp.buddycon.designsystem.component.modal.GifticonInfoListModalSheet
import com.yapp.buddycon.designsystem.component.modal.GifticonInfoModalSheetContent
import com.yapp.buddycon.designsystem.component.snackbar.BuddyConSnackbar
import com.yapp.buddycon.designsystem.component.snackbar.showBuddyConSnackBar
import com.yapp.buddycon.designsystem.theme.Black
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Grey70
import com.yapp.buddycon.designsystem.theme.Paddings
import com.yapp.buddycon.domain.model.gifticon.GifticonModel
import com.yapp.buddycon.domain.model.type.GifticonStore
import com.yapp.buddycon.utility.RequestLocationPermission
import com.yapp.buddycon.utility.checkLocationPermission
import com.yapp.buddycon.utility.getCurrentLocation
import timber.log.Timber
import java.util.Calendar

// TopAppBarHeight(52) + BottomNavigationBarHeight(72) + MapCategoryTabHeight(60)
private const val MapBarSize = 184f

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    mapViewModel: MapViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var currentLocation by remember { mutableStateOf<Location?>(null) }

    val configuration = LocalConfiguration.current
    val mapHeightDp = configuration.screenHeightDp.toFloat() - MapBarSize
    val mapUiState by mapViewModel.uiState.collectAsStateWithLifecycle()

    RequestLocationPermission(
        onGranted = {
            showBuddyConSnackBar(
                message = context.getString(R.string.map_location_permission),
                scope = coroutineScope,
                snackbarHostState = snackbarHostState
            )
        }
    )
    BottomSheetScaffold(
        sheetContent = {
            MapBottomSheet(
                mapViewModel = mapViewModel,
                mapHeightDp = mapHeightDp
            )
        },
        sheetPeekHeight = mapUiState.heightDp.dp,
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
            snackbarHostState = snackbarHostState,
            location = currentLocation
        ) {
            getCurrentLocation(context) {
                currentLocation = it
            }
        }
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
    val mapUiState by mapViewModel.uiState.collectAsStateWithLifecycle()

    // 삭제 예정
    val today = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time.time

    Box(
        modifier = modifier
            .heightIn(min = BottomSheetValue.Collapsed.sheetPeekHeightDp.dp, max = BottomSheetValue.Expanded.sheetPeekHeightDp.dp)
            .fillMaxSize()
            .background(BuddyConTheme.colors.background)
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onDragEnd = {
                        mapViewModel.onSheetValueChange(
                            sheetValue = when (mapUiState.heightDp) {
                                in BottomSheetValue.PartiallyExpanded.sheetPeekHeightDp..(BottomSheetValue.Expanded.sheetPeekHeightDp / 2f - 1) -> {
                                    BottomSheetValue.PartiallyExpanded
                                }

                                in (BottomSheetValue.Expanded.sheetPeekHeightDp / 2f)..mapHeightDp -> BottomSheetValue.Expanded
                                else -> BottomSheetValue.Collapsed
                            }
                        )
                    },
                    onVerticalDrag = { change, dragAmount ->
                        if (mapUiState.heightDp - (dragAmount / density) in 0f..mapHeightDp) {
                            mapViewModel.changeBottomSheetOffset(dragAmount / density)
                        }
                    }
                )
            }
    ) {
        when (mapUiState.sheetValue) {
            BottomSheetValue.Expanded -> {
                GifticonInfoListModalSheet(
                    countOfUsableGifticon = 4,
                    countOfImminetGifticon = 1,
                    gifticonInfos = List(5) {
                        GifticonModel(
                            imageUrl = "https://github.com/Team-BuddyCon/ANDROID_V2/assets/34837583/5ab80674-4ffb-4c91-ab10-3743d8c87e58",
                            category = GifticonStore.STARBUCKS,
                            name = "빙그레)바나나맛우유240",
                            expirationTime = (today + 1000 * 60 * 60 * 24L * (-1..366).random())
                        )
                    }
                )
            }

            else -> {
                GifticonInfoModalSheetContent(
                    countOfUsableGifticon = 12,
                    countOfImminetGifticon = 1
                )
            }
        }
    }
}

@Composable
private fun MapContent(
    modifier: Modifier = Modifier,
    mapViewModel: MapViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    location: Location?,
    onGranted: () -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // 위치 권한 
    var isGrantedPermission by remember { mutableStateOf(checkLocationPermission(context)) }

    // 위치 권한 유도 팝업
    var showPermissonDialog by remember { mutableStateOf(false) }

    // 위치 권한 요청
    var requestPermissionEvent by remember { mutableStateOf(false) }

    val uiState by mapViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(location) {
        location?.let { location ->
            // TODO location 기반 API 요청
        }
    }

    // 권한 있을 경우 -> 현재 위치 요청
    if (isGrantedPermission) {
        onGranted()
    }

    if (showPermissonDialog) {
        DefaultDialog(
            dialogTitle = stringResource(R.string.location_permission_dialog_title),
            dismissText = stringResource(R.string.location_permission_later),
            confirmText = stringResource(R.string.location_permission_granted),
            dialogContent = stringResource(R.string.location_permission_dialog_content),
            onConfirm = {
                requestPermissionEvent = true
                showPermissonDialog = false
            },
            onDismissRequest = { showPermissonDialog = false }
        )
    }

    if (requestPermissionEvent) {
        RequestLocationPermission(
            onGranted = {
                showBuddyConSnackBar(
                    message = context.getString(R.string.map_location_permission),
                    scope = coroutineScope,
                    snackbarHostState = snackbarHostState
                )
                isGrantedPermission = true
            },
            onDeny = {
                isGrantedPermission = false
                requestPermissionEvent = false
            }
        )
    }

    Column(modifier) {
        MapCategoryTab(
            category = uiState.category,
            onCategoryChange = { mapViewModel.onCategoryChange(it) }
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
//                                location?.let { location ->
//                                    kakaoMap.labelManager?.let { manager ->
//                                        searchPlaceModels.forEach { seachPlaceModel ->
//                                            getLocationLabel(
//                                                labelManager = manager,
//                                                latitude = seachPlaceModel.y.toDouble(),
//                                                longitude = seachPlaceModel.x.toDouble(),
//                                                store = store
//                                            )
//                                        }
//                                    }
//                                }
                            }

                            override fun getPosition(): LatLng {
                                location?.let { location ->
                                    Timber.d("currentLocation is not null")
                                    return LatLng.from(location.latitude, location.longitude)
                                } ?: kotlin.run {
                                    Timber.d("currentLocation is null")
                                    return super.getPosition()
                                }
                            }
                        }
                    )
                }
            )

            if (isGrantedPermission.not()) {
                Spacer(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Black.copy(0.4f))
                )
                Card(
                    modifier = Modifier
                        .padding(top = Paddings.xlarge)
                        .align(Alignment.TopCenter)
                        .height(45.dp)
                        .clickable { showPermissonDialog = true },
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
                            text = stringResource(R.string.map_location_not_granted_permission),
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
