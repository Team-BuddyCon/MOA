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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.component.appbar.TopAppBarWithBack
import com.yapp.buddycon.designsystem.component.button.BuddyConButton
import com.yapp.buddycon.designsystem.component.custom.FullGifticonImage
import com.yapp.buddycon.designsystem.component.dialog.ConfirmDialog
import com.yapp.buddycon.designsystem.component.dialog.DefaultDialog
import com.yapp.buddycon.designsystem.component.input.EssentialInputSelectDate
import com.yapp.buddycon.designsystem.component.input.EssentialInputSelectUsage
import com.yapp.buddycon.designsystem.component.input.EssentialInputText
import com.yapp.buddycon.designsystem.component.input.NoEssentialInputText
import com.yapp.buddycon.designsystem.component.modal.CalendarModalSheet
import com.yapp.buddycon.designsystem.component.modal.CategoryModalSheet
import com.yapp.buddycon.designsystem.component.snackbar.BuddyConSnackbar
import com.yapp.buddycon.designsystem.component.snackbar.showBuddyConSnackBar
import com.yapp.buddycon.designsystem.theme.Black
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Grey30
import com.yapp.buddycon.designsystem.theme.Grey70
import com.yapp.buddycon.designsystem.theme.Paddings
import com.yapp.buddycon.domain.model.type.GifticonCategory
import com.yapp.buddycon.gifticon.GifticonViewModel
import timber.log.Timber
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun GifticonRegisterScreen(
    gifticonViewModel: GifticonViewModel = hiltViewModel(),
    gifticonRegisterViewModel: GifticonRegisterViewModel = hiltViewModel(),
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var showedSnackbar by remember { mutableStateOf(false) }
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        try {
            val image = uri?.let { InputImage.fromFilePath(context, it) }
            val scanner = BarcodeScanning.getClient()
            image?.let { image ->
                scanner.process(image)
                    .addOnSuccessListener { barcodes ->
                        if (barcodes.isNotEmpty()) {
                            imageUri = uri
                        } else {
                            Timber.d("There is not barcode in image")
                            gifticonViewModel.showGifticonRegisterError(true)
                            onBack()
                        }
                    }.addOnFailureListener {
                        Timber.e("barcode scanner detection error: ${it.message}")
                        gifticonViewModel.showGifticonRegisterError(true)
                        onBack()
                    }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    val uiState by gifticonRegisterViewModel.uiState.collectAsStateWithLifecycle()
    var showErrorPopup by remember { mutableStateOf(false) }
    var stopRegisterPopup by remember { mutableStateOf(false) }

    LaunchedEffect(imageUri) {
        if (imageUri == null) {
            galleryLauncher.launch("image/*")
        }
    }

    LaunchedEffect(Unit) {
        gifticonRegisterViewModel.isCompleted.collect { isCompleted ->
            if (isCompleted) {
                showBuddyConSnackBar(
                    message = context.getString(R.string.gifticon_register_success),
                    scope = coroutineScope,
                    snackbarHostState = snackbarHostState
                )
            }
        }
    }

    if (imageUri != null && showedSnackbar.not()) {
        showBuddyConSnackBar(
            message = context.getString(R.string.gifticon_register_snackbar),
            scope = coroutineScope,
            snackbarHostState = snackbarHostState
        )
        showedSnackbar = true
    }

    if (showErrorPopup) {
        ConfirmDialog(
            dialogTitle = stringResource(
                if (uiState.name.length > 16) {
                    R.string.gifticon_register_max_length_name
                } else if (uiState.name.isEmpty()) {
                    R.string.gifticon_register_empty_name
                } else if (uiState.expireDate == 0L) {
                    R.string.gifticon_register_empty_expire_date
                } else {
                    R.string.gifticon_register_max_length_memo
                }
            ),
            dialogContent = if (uiState.name.length > 16) {
                stringResource(R.string.gifticon_register_max_length_name_description)
            } else {
                null
            },
            onClick = { showErrorPopup = false },
            onDismissRequest = { showErrorPopup = false }
        )
    }

    if (stopRegisterPopup) {
        DefaultDialog(
            dialogTitle = stringResource(R.string.gifticon_register_stop),
            dismissText = stringResource(R.string.gifticon_register_stop_dismiss),
            confirmText = stringResource(R.string.gifticon_register_stop_confirm),
            onDismissRequest = { stopRegisterPopup = false },
            onConfirm = {
                stopRegisterPopup = false
                onBack()
            }
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
                    onClick = { stopRegisterPopup = true }
                )
                BuddyConButton(
                    text = stringResource(R.string.gifticon_save),
                    modifier = Modifier
                        .padding(start = Paddings.xlarge)
                        .weight(1f),
                    containerColor = BuddyConTheme.colors.primary,
                    contentColor = BuddyConTheme.colors.onPrimary,
                    onClick = {
                        if (uiState.name.isEmpty() ||
                            uiState.name.length > 16 ||
                            uiState.expireDate == 0L ||
                            uiState.memo.length > 50
                        ) {
                            showErrorPopup = true
                        } else {
                            if (uiState.category != GifticonCategory.ETC) {
                                gifticonRegisterViewModel.registerNewGifticon(
                                    imagePath = imageUri?.toString() ?: ""
                                )
                            }
                        }
                    }
                )
            }
        },
        snackbarHost = { BuddyConSnackbar(snackbarHostState = snackbarHostState) }
    ) { paddingValues ->
        GifticonRegisterContent(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(BuddyConTheme.colors.background),
            imageUri = imageUri,
            uiState = uiState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GifticonRegisterContent(
    modifier: Modifier = Modifier,
    gifticonRegisterViewModel: GifticonRegisterViewModel = hiltViewModel(),
    imageUri: Uri? = null,
    uiState: GifticonRegisterUiState
) {
    val scrollState = rememberScrollState()
    var isImageExpanded by remember { mutableStateOf(false) }
    var isShowCalendarModal by remember { mutableStateOf(false) }
    var isShowCategoryModal by remember { mutableStateOf(false) }

    if (isShowCalendarModal) {
        CalendarModalSheet(
            onSelectDate = { gifticonRegisterViewModel.setExpirationDate(it) },
            onDismiss = { isShowCalendarModal = false }
        )
    }

    if (isShowCategoryModal) {
        CategoryModalSheet(
            onSelectCategory = { gifticonRegisterViewModel.setCategory(it) },
            onDismiss = { isShowCategoryModal = false }
        )
    }

    FullGifticonImage(
        imageUri = imageUri,
        isExpanded = isImageExpanded,
        onExpandChanged = { isImageExpanded = it }
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
                    .clickable { isImageExpanded = true },
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
        EssentialInputText(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxSize(),
            title = stringResource(R.string.gifticon_name),
            placeholder = stringResource(R.string.gifticon_name_placeholder),
            value = uiState.name,
            onValueChange = { gifticonRegisterViewModel.setName(it) }
        )
        EssentialInputSelectDate(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.gifticon_expiration_date),
            placeholder = stringResource(R.string.gifticon_expiration_date_placeholder),
            value = if (uiState.expireDate == 0L) {
                ""
            } else {
                SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA).format(uiState.expireDate)
            },
            action = { isShowCalendarModal = true }
        )
        EssentialInputSelectUsage(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.gifticon_usage),
            placeholder = stringResource(R.string.gifticon_usage_placeholder),
            value = if (uiState.category == GifticonCategory.ETC) "" else uiState.category.value,
            action = { isShowCategoryModal = true }
        )
        NoEssentialInputText(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.gifticon_memo),
            placeholder = stringResource(R.string.gifticon_memo_placeholder),
            value = uiState.memo,
            onValueChange = { gifticonRegisterViewModel.setMemo(it) }
        )
    }
}
