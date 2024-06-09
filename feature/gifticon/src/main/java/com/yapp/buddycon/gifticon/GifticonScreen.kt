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
import com.yapp.buddycon.utility.findActivity

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
    val isGrantedPermission by remember {
        mutableStateOf(checkReadExternalStorage(context))
    }

    // TODO 권한 없을 시 팝업 노출
    if (!isGrantedPermission) {
        RequestReadExternalStoragePermission {
            context.findActivity().finish()
        }
    }

    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = { FloatingActionButton(onNavigateToRegister) }
    ) { paddingValues ->
        GifticonContent(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(BuddyConTheme.colors.background),
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
    onNavigateToGifticonDetail: (Int) -> Unit,
    afterGifticonRegistrationCompletes: Boolean?
) {
    Column(modifier) {
        AvailabeGifticonScreen(
            onNavigateToGifticonDetail = { gifticonId ->
                onNavigateToGifticonDetail(gifticonId)
            },
            afterGifticonRegistrationCompletes = afterGifticonRegistrationCompletes
        )
    }
}
