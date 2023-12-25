package com.yapp.buddycon.gifticon.register

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.component.appbar.TopAppBarWithBack
import com.yapp.buddycon.designsystem.component.button.BuddyConButton
import com.yapp.buddycon.designsystem.component.snackbar.BuddyConSnackbar
import com.yapp.buddycon.designsystem.component.snackbar.showBuddyConSnackBar
import com.yapp.buddycon.designsystem.theme.Black
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Grey30
import com.yapp.buddycon.designsystem.theme.Grey70
import com.yapp.buddycon.designsystem.theme.Paddings

@Composable
fun GifticonRegisterScreen(
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imageUri = uri
    }

    LaunchedEffect(Unit) {
        galleryLauncher.launch("image/*")
    }

    if (imageUri != null) {
        showBuddyConSnackBar(
            message = context.getString(R.string.gifticon_register_snackbar),
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
        snackbarHost = { BuddyConSnackbar(snackbarHostState) }
    ) { paddingValues ->
        GifticonRegisterContent(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(BuddyConTheme.colors.background),
            imageUri = imageUri
        )
    }
}

@Composable
private fun GifticonRegisterContent(
    modifier: Modifier = Modifier,
    imageUri: Uri? = null
) {
    var isExpanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    FullGifticonImage(
        imageUri = imageUri,
        isExpanded = isExpanded,
        onExpandChanged = { isExpanded = it }
    )
    Column(modifier.verticalScroll(scrollState)) {
        Box(
            modifier = Modifier
                .padding(top = Paddings.medium)
                .padding(horizontal = Paddings.xlarge)
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(20.dp))
        ) {
            AsyncImage(
                model = imageUri,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 12.dp, end = 12.dp)
                    .size(40.dp)
                    .background(Black.copy(0.4f), CircleShape)
                    .clickable { isExpanded = true },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
            }
        }
    }
}

@Composable
private fun FullGifticonImage(
    imageUri: Uri?,
    isExpanded: Boolean = false,
    onExpandChanged: (Boolean) -> Unit = {}
) {
    if (isExpanded) {
        Dialog(
            onDismissRequest = { onExpandChanged(isExpanded.not()) },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 27.dp, horizontal = Paddings.xlarge)
                    .fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_white_close),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.End)
                        .size(24.dp)
                        .clickable { onExpandChanged(isExpanded.not()) },
                    tint = Color.Unspecified
                )
                AsyncImage(
                    model = imageUri,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = Paddings.medium)
                        .fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
}
