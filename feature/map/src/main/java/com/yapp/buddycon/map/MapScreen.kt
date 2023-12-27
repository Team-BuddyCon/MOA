package com.yapp.buddycon.map

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.component.appbar.TopAppBarWithNotification
import com.yapp.buddycon.designsystem.component.button.CategoryButton
import com.yapp.buddycon.designsystem.component.modal.GifticonInfoModalSheetContent
import com.yapp.buddycon.designsystem.component.snackbar.BuddyConSnackbar
import com.yapp.buddycon.designsystem.component.snackbar.showBuddyConSnackBar
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Paddings
import com.yapp.buddycon.domain.model.type.GifticonCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
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

    BottomSheetScaffold(
        sheetContent = {
            GifticonInfoModalSheetContent(
                modifier = Modifier.padding(bottom = 11.dp),
                countOfUsableGifticon = 12,
                countOfImminetGifticon = 1
            )
        },
        scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = SheetState(
                skipPartiallyExpanded = false,
                initialValue = SheetValue.Expanded,
                skipHiddenState = true
            )
        ),
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
                modifier = Modifier.padding(top = 128.dp),
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
    }
}

@Composable
private fun MapCategoryTab(
    category: GifticonCategory,
    onCategoryChange: (GifticonCategory) -> Unit = { }
) {
    LazyRow(
        modifier = Modifier
            .padding(Paddings.xlarge)
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
