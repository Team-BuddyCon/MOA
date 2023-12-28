package com.yapp.buddycon.map

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.component.appbar.TopAppBarWithNotification
import com.yapp.buddycon.designsystem.component.button.CategoryButton
import com.yapp.buddycon.designsystem.component.modal.GifticonInfoModalSheetContent
import com.yapp.buddycon.designsystem.component.snackbar.BuddyConSnackbar
import com.yapp.buddycon.designsystem.component.snackbar.showBuddyConSnackBar
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Paddings
import com.yapp.buddycon.domain.model.type.GifticonCategory
import com.yapp.buddycon.utility.toDp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen() {
    val snackbarHostState = remember { SnackbarHostState() }
    var offsetY by remember { mutableStateOf(111f) }

    RequestLocationPermission(snackbarHostState)
    BottomSheetScaffold(
        sheetContent = {
            MapBottomSheet(
                modifier = Modifier
                    .background(BuddyConTheme.colors.background)
                    .draggable(
                        state = rememberDraggableState { delta ->
                            if (offsetY > 0) {
                                offsetY -= delta
                            }
                        },
                        orientation = Orientation.Vertical
                    )
            )
        },
        sheetPeekHeight = offsetY.toDp(),
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetContainerColor = BuddyConTheme.colors.background,
        sheetShadowElevation = 33.dp,
        topBar = {
            TopAppBarWithNotification(
                title = stringResource(R.string.map)
            )
        },
        sheetDragHandle = null,
        snackbarHost = {
            BuddyConSnackbar(
                modifier = Modifier.padding(top = 162.dp),
                snackbarHostState = snackbarHostState,
                contentAlignment = Alignment.TopCenter
            )
        }
    ) { paddingValues ->
        MapContent(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(BuddyConTheme.colors.background)
        )
    }
}

@Composable
private fun MapBottomSheet(
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        GifticonInfoModalSheetContent(
            countOfUsableGifticon = 12,
            countOfImminetGifticon = 1
        )
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
            modifier = Modifier.fillMaxSize()
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
