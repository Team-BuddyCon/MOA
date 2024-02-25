package com.yapp.buddycon.gifticon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.component.appbar.TopAppBarWithNotification
import com.yapp.buddycon.designsystem.component.button.FloatingActionButton
import com.yapp.buddycon.designsystem.component.dialog.ConfirmDialog
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.gifticon.available.AvailabeGifticonScreen

@Composable
fun GifticonScreeen(
    gifticonViewModel: GifticonViewModel = hiltViewModel(),
    onNavigateToRegister: () -> Unit = {}
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

    Scaffold(
        topBar = {
            TopAppBarWithNotification(stringResource(R.string.gifticon))
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = { FloatingActionButton(onNavigateToRegister) }
    ) { paddingValues ->
        GifticonContent(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(BuddyConTheme.colors.background)
        )
    }
}

@Composable
fun GifticonContent(
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        AvailabeGifticonScreen()
    }
}
