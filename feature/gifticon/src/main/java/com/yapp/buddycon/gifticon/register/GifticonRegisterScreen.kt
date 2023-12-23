package com.yapp.buddycon.gifticon.register

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.component.appbar.TopAppBarWithBack
import com.yapp.buddycon.designsystem.component.button.BuddyConButton
import com.yapp.buddycon.designsystem.component.snackbar.BuddyConSnackbar
import com.yapp.buddycon.designsystem.component.snackbar.showBuddyConSnackBar
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Grey30
import com.yapp.buddycon.designsystem.theme.Grey70
import com.yapp.buddycon.designsystem.theme.Paddings

@Composable
fun GifticonRegisterScreen(
    onBack: () -> Unit = {}
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imageUri = uri
    }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        galleryLauncher.launch("image/*")
    }

    if (imageUri != null) {
        showBuddyConSnackBar(
            message = stringResource(R.string.gifticon_register_snackbar),
            scope = coroutineScope,
            snackbarHostState = snackbarHostState
        )
    }

    Scaffold(
        topBar = {
            TopAppBarWithBack(
                title = stringResource(R.string.gifticon_register),
                onBack = onBack
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .background(BuddyConTheme.colors.background)
                    .padding(bottom = Paddings.xlarge)
                    .padding(horizontal = Paddings.xlarge)
            ) {
                BuddyConButton(
                    text = stringResource(R.string.gifticon_cancel),
                    modifier = Modifier.weight(1f),
                    containerColor = Grey30,
                    contentColor = Grey70,
                    onClick = { }
                )
                BuddyConButton(
                    text = stringResource(R.string.gifticon_save),
                    modifier = Modifier
                        .padding(start = Paddings.xlarge)
                        .weight(1f),
                    containerColor = BuddyConTheme.colors.primary,
                    contentColor = BuddyConTheme.colors.onPrimary,
                    onClick = { }
                )
            }
        },
        snackbarHost = {
            BuddyConSnackbar(snackbarHostState)
        }
    ) { paddingValues ->
        GifticonRegisterContent(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(BuddyConTheme.colors.background)
        )
    }
}

@Composable
private fun GifticonRegisterContent(
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(modifier.verticalScroll(scrollState)) {
    }
}
