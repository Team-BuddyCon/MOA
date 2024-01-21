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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import java.util.Calendar

// TopAppBarHeight(52) + BottomNavigationBarHeight(72) + MapCategoryTabHeight(60)
private const val MapBarSize = 184f

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    mapViewModel: MapViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val configuration = LocalConfiguration.current
    val mapHeightDp = configuration.screenHeightDp.toFloat() - MapBarSize
    val mapUiState by mapViewModel.uiState.collectAsStateWithLifecycle()

    RequestLocationPermission(snackbarHostState)
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
                            category = GifticonCategory.STARBUCKS,
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
            onCategoryChange = { mapViewModel.onCategoryChange(it) }
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
            .height(60.dp)
            .fillMaxWidth()
            .padding(horizontal = Paddings.xlarge)
            .padding(top = Paddings.xlarge, bottom = Paddings.large),
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
