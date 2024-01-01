package com.yapp.buddycon.map

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.component.appbar.TopAppBarWithNotification
import com.yapp.buddycon.designsystem.component.button.CategoryButton
import com.yapp.buddycon.designsystem.component.modal.GifticonInfoListModalSheet
import com.yapp.buddycon.designsystem.component.modal.GifticonInfoModalSheetContent
import com.yapp.buddycon.designsystem.component.snackbar.BuddyConSnackbar
import com.yapp.buddycon.designsystem.component.snackbar.showBuddyConSnackBar
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Paddings
import com.yapp.buddycon.domain.model.gifticon.GifticonModel
import com.yapp.buddycon.domain.model.type.GifticonCategory
import com.yapp.buddycon.utility.toPx
import java.util.Calendar

private const val MapBarSize = 260f

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    mapViewModel: MapViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val configuration = LocalConfiguration.current
    val mapHeightDp = configuration.screenHeightDp - MapBarSize
    val mapUiState by mapViewModel.uiState.collectAsStateWithLifecycle()

    RequestLocationPermission(snackbarHostState)
    BottomSheetScaffold(
        sheetContent = {
            MapBottomSheet(
                modifier = Modifier
                    .background(BuddyConTheme.colors.background)
                    .height(mapHeightDp.dp),
                mapHeightDp = mapHeightDp
            )
        },
        sheetPeekHeight = mapUiState.heightDp.dp,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetContainerColor = BuddyConTheme.colors.background,
        sheetShadowElevation = 33.dp,
        sheetDragHandle = null,
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
        }
    ) {
        MapContent(
            modifier = Modifier
                .fillMaxSize()
                .background(BuddyConTheme.colors.background)
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
    val mapHeightPx = mapHeightDp.dp.toPx()
    val mapUiState by mapViewModel.uiState.collectAsStateWithLifecycle()
    var prevSheetValue: BottomSheetValue by remember { mutableStateOf(BottomSheetValue.Collapsed) }
    val heightPx by rememberUpdatedState(mapUiState.heightDp * density)

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onDragStart = {
                        prevSheetValue = mapUiState.sheetValue
                        mapViewModel.changeSheetValue(BottomSheetValue.Moving(mapUiState.heightDp))
                    },
                    onDragEnd = {
                        prevSheetValue = mapUiState.sheetValue
                        mapViewModel.changeSheetValue(
                            when (mapUiState.heightDp) {
                                in BottomSheetValue.PartiallyExpanded.sheetPeekHeightDp..(mapHeightDp / 2f - 1) -> {
                                    BottomSheetValue.PartiallyExpanded
                                }

                                in (mapHeightDp / 2f)..mapHeightDp -> BottomSheetValue.Expanded
                                else -> BottomSheetValue.Collapsed
                            }
                        )
                    },
                    onVerticalDrag = { change, dragAmount ->
                        if ((heightPx - dragAmount) in 0f..mapHeightPx) {
                            mapViewModel.changeHeightDp((heightPx - dragAmount) / density)
                        }
                    }
                )
            }
    ) {
        if (mapUiState.sheetValue is BottomSheetValue.Moving) {
            MapBottomSheetContent(prevSheetValue)
        } else {
            MapBottomSheetContent(mapUiState.sheetValue)
        }
    }
}

@Composable
private fun MapBottomSheetContent(
    sheetValue: BottomSheetValue
) {
    val today = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time.time

    when (sheetValue) {
        BottomSheetValue.Collapsed,
        BottomSheetValue.PartiallyExpanded -> {
            GifticonInfoModalSheetContent(
                countOfUsableGifticon = 12,
                countOfImminetGifticon = 1
            )
        }

        BottomSheetValue.Expanded -> {
            GifticonInfoListModalSheet(
                modifier = Modifier.fillMaxHeight(),
                countOfUsableGifticon = 4,
                countOfImminetGifticon = 1,
                gifticonInfos = List(10) {
                    GifticonModel(
                        imageUrl = "https://github.com/Team-BuddyCon/ANDROID_V2/assets/34837583/5ab80674-4ffb-4c91-ab10-3743d8c87e58",
                        category = GifticonCategory.STARBUCKS,
                        name = "빙그레)바나나맛우유240",
                        expirationTime = (today + 1000 * 60 * 60 * 24L * (-1..366).random())
                    )
                }
            )
        }

        else -> Unit
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
private fun MapContent(
    modifier: Modifier = Modifier,
    mapViewModel: MapViewModel = hiltViewModel()
) {
    val uiState by mapViewModel.uiState.collectAsStateWithLifecycle()
    Column(modifier) {
        MapCategoryTab(
            category = uiState.category,
            onCategoryChange = { mapViewModel.changeCategory(it) }
        )
        NaverMap(
            modifier = Modifier.fillMaxSize(),
            uiSettings = MapUiSettings(
                isZoomControlEnabled = false,
                isLogoClickEnabled = false,
                isScaleBarEnabled = false
            )
        )
    }
}

@Composable
private fun MapCategoryTab(
    category: GifticonCategory,
    onCategoryChange: (GifticonCategory) -> Unit = { }
) {
    LazyRow(
        modifier = Modifier
            .padding(horizontal = Paddings.xlarge)
            .padding(top = Paddings.xlarge, bottom = Paddings.large)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Paddings.small)
    ) {
        items(GifticonCategory.values()) {
            CategoryButton(
                gifticonCategory = it,
                isSelected = it == category,
                onClick = { onCategoryChange(it) }
            )
        }
    }
}

@Composable
private fun RequestLocationPermission(snackbarHostState: SnackbarHostState) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    val permissionsLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val isGranted = permissions.values.all { it }
        if (isGranted) {
            showBuddyConSnackBar(
                message = context.getString(R.string.map_location_permission),
                scope = coroutineScope,
                snackbarHostState = snackbarHostState
            )
        }
    }

    LaunchedEffect(Unit) {
        if (permissions.any { ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_DENIED }) {
            permissionsLauncher.launch(permissions)
        }
    }
}
