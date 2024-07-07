package com.yapp.buddycon.gifticon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.component.button.FloatingActionButton
import com.yapp.buddycon.designsystem.component.dialog.ConfirmDialog
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.gifticon.available.AvailabeGifticonScreen
import com.yapp.buddycon.utility.RequestReadExternalStoragePermission
import com.yapp.buddycon.utility.checkReadExternalStorage
import com.yapp.buddycon.utility.navigateToSetting

@Composable
fun GifticonScreeen(
    gifticonViewModel: GifticonViewModel = hiltViewModel(),
    onNavigateToRegister: () -> Unit,
    onNavigateToGifticonDetail: (Int) -> Unit,
    afterGifticonRegistrationCompletes: Boolean?
) {
    val showErrorPopup by gifticonViewModel.showErrorPopup.collectAsStateWithLifecycle()
    if (showErrorPopup) {
        ConfirmDialog(
            dialogTitle = stringResource(R.string.gifticon_register_error),
            dialogContent = stringResource(R.string.gifticon_register_error_reason),
            onClick = { gifticonViewModel.showGifticonRegisterError(false) },
            onDismissRequest = { gifticonViewModel.showGifticonRegisterError(false) }
        )
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(BuddyConTheme.colors.background)

    val context = LocalContext.current
    var isGrantedPermission by remember { mutableStateOf(checkReadExternalStorage(context)) }
    var showExternalStoragePopup by remember { mutableStateOf(false) }
    var requestPermission by remember { mutableStateOf(false) }
    val showCoachMark by gifticonViewModel.showCoachMark.collectAsStateWithLifecycle()
    val firstExternalStorage by gifticonViewModel.firstExternalStorage.collectAsStateWithLifecycle()

    if (showExternalStoragePopup) {
        ConfirmDialog(
            dialogTitle = stringResource(R.string.gifticon_external_storage_popup_title),
            dialogContent = stringResource(R.string.gifticon_external_Storage_popup_description),
            onClick = {
                if (firstExternalStorage) {
                    requestPermission = true
                    gifticonViewModel.checkFirstExternalStorage()
                } else {
                    navigateToSetting(
                        context = context,
                        packageName = context.packageName
                    )
                }
                showExternalStoragePopup = false
            },
            onDismissRequest = { showExternalStoragePopup = false },
            buttonText = stringResource(R.string.granted)
        )
    }

    if (requestPermission) {
        RequestReadExternalStoragePermission(
            onGranted = {
                isGrantedPermission = checkReadExternalStorage(context)
                requestPermission = false
            },
            onDeny = {
                requestPermission = false
            }
        )
    }

    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton {
                isGrantedPermission = checkReadExternalStorage(context)
                if (isGrantedPermission) {
                    onNavigateToRegister()
                } else {
                    showExternalStoragePopup = true
                }
            }
        }
    ) { paddingValues ->
        GifticonContent(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(BuddyConTheme.colors.background),
            showCoachMark = showCoachMark,
            onCloseCoachMark = {
                gifticonViewModel.stopCoachMark()
            },
            onNavigateToGifticonDetail = { gifticonId ->
                onNavigateToGifticonDetail(gifticonId)
            },
            afterGifticonRegistrationCompletes = afterGifticonRegistrationCompletes
        )
    }
}

@Composable
fun GifticonContent(
    modifier: Modifier = Modifier,
    showCoachMark: Boolean = false,
    onCloseCoachMark: () -> Unit = {},
    onNavigateToGifticonDetail: (Int) -> Unit,
    afterGifticonRegistrationCompletes: Boolean?
) {
    Column(modifier) {
        AvailabeGifticonScreen(
            showCoachMark = showCoachMark,
            onCloseCoachMark = onCloseCoachMark,
            onNavigateToGifticonDetail = { gifticonId ->
                onNavigateToGifticonDetail(gifticonId)
            },
            afterGifticonRegistrationCompletes = afterGifticonRegistrationCompletes
        )
    }
}
