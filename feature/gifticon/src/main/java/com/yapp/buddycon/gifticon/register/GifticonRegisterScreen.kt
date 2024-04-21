package com.yapp.buddycon.gifticon.register

import android.content.Context
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
import com.yapp.buddycon.domain.model.type.GifticonStore
import com.yapp.buddycon.gifticon.GifticonViewModel
import timber.log.Timber
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun GifticonRegisterScreen(
    gifticonViewModel: GifticonViewModel = hiltViewModel(),
    gifticonRegisterViewModel: GifticonRegisterViewModel = hiltViewModel(),
    onNavigateToGifticonDetail: (Int) -> Unit = {},
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var showedSnackbar by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    var showErrorPopup by remember { mutableStateOf(false) }
    var showStopRegisterPopup by remember { mutableStateOf(false) }

    val gifticonRegisterUiState by gifticonRegisterViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        gifticonRegisterViewModel.gifticonId.collect { gifticonId ->
            showBuddyConSnackBar(
                message = context.getString(R.string.gifticon_register_success),
                scope = coroutineScope,
                snackbarHostState = snackbarHostState
            )
            onNavigateToGifticonDetail(gifticonId)
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
                if (gifticonRegisterUiState.name.length > 16) {
                    R.string.gifticon_register_max_length_name
                } else if (gifticonRegisterUiState.name.isEmpty()) {
                    R.string.gifticon_register_empty_name
                } else if (gifticonRegisterUiState.expireDate == 0L) {
                    R.string.gifticon_register_empty_expire_date
                } else if (gifticonRegisterUiState.category == GifticonStore.NONE) {
                    R.string.gifticon_register_empty_store_category
                } else {
                    R.string.gifticon_register_max_length_memo
                }
            ),
            dialogContent = if (gifticonRegisterUiState.name.length > 16) {
                stringResource(R.string.gifticon_register_max_length_name_description)
            } else {
                null
            },
            onClick = { showErrorPopup = false },
            onDismissRequest = { showErrorPopup = false }
        )
    }

    if (showStopRegisterPopup) {
        DefaultDialog(
            dialogTitle = stringResource(R.string.gifticon_register_stop),
            dismissText = stringResource(R.string.gifticon_register_stop_dismiss),
            confirmText = stringResource(R.string.gifticon_register_stop_confirm),
            onDismissRequest = { showStopRegisterPopup = false },
            onConfirm = {
                showStopRegisterPopup = false
                onBack()
            }
        )
    }

    if (imageUri == null) {
        SelectGifticonImageScreen(
            context = context,
            onSuccess = {
                imageUri = it
            },
            onError = {
                gifticonViewModel.showGifticonRegisterError(true)
                onBack()
            }
        )
    } else {
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
                        onClick = { showStopRegisterPopup = true }
                    )
                    BuddyConButton(
                        text = stringResource(R.string.gifticon_save),
                        modifier = Modifier
                            .padding(start = Paddings.xlarge)
                            .weight(1f),
                        containerColor = BuddyConTheme.colors.primary,
                        contentColor = BuddyConTheme.colors.onPrimary,
                        onClick = {
                            if (gifticonRegisterUiState.name.isEmpty() ||
                                gifticonRegisterUiState.name.length > 16 ||
                                gifticonRegisterUiState.expireDate == 0L ||
                                gifticonRegisterUiState.category == GifticonStore.NONE ||
                                gifticonRegisterUiState.memo.length > 50
                            ) {
                                showErrorPopup = true
                            } else {
                                gifticonRegisterViewModel.registerNewGifticon(
                                    imagePath = imageUri?.toString() ?: ""
                                )
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
                imageUri = imageUri
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GifticonRegisterContent(
    modifier: Modifier = Modifier,
    gifticonRegisterViewModel: GifticonRegisterViewModel = hiltViewModel(),
    imageUri: Uri? = null
) {
    val scrollState = rememberScrollState()
    val gifticonRegisterUiState by gifticonRegisterViewModel.uiState.collectAsStateWithLifecycle()
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
        NoEssentialInputText(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxSize(),
            title = stringResource(R.string.gifticon_name),
            placeholder = stringResource(R.string.gifticon_name_placeholder),
            value = gifticonRegisterUiState.name,
            onValueChange = { gifticonRegisterViewModel.setName(it) }
        )
        EssentialInputSelectDate(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.gifticon_expiration_date),
            placeholder = stringResource(R.string.gifticon_expiration_date_placeholder),
            value = if (gifticonRegisterUiState.expireDate == 0L) {
                ""
            } else {
                SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA).format(gifticonRegisterUiState.expireDate)
            },
            action = { isShowCalendarModal = true }
        )
        EssentialInputSelectUsage(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.gifticon_usage),
            placeholder = stringResource(R.string.gifticon_usage_placeholder),
            value = if (gifticonRegisterUiState.category == GifticonStore.ETC) "" else gifticonRegisterUiState.category.value,
            action = { isShowCategoryModal = true }
        )
        NoEssentialInputText(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.gifticon_memo),
            placeholder = stringResource(R.string.gifticon_memo_placeholder),
            value = gifticonRegisterUiState.memo,
            onValueChange = { gifticonRegisterViewModel.setMemo(it) }
        )
    }
}

@Composable
private fun SelectGifticonImageScreen(
    context: Context,
    onSuccess: (Uri) -> Unit = {},
    onError: () -> Unit = {}
) {
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        try {
            val image = uri?.let { InputImage.fromFilePath(context, it) }
            val scanner = BarcodeScanning.getClient()
            image?.let { image ->
                scanner.process(image)
                    .addOnSuccessListener { barcodes ->
                        if (barcodes.isNotEmpty()) {
                            onSuccess(uri)
                        } else {
                            Timber.d("There is not barcode in image")
                            onError()
                        }
                    }.addOnFailureListener {
                        Timber.e("barcode scanner detection error: ${it.message}")
                        onError()
                    }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    LaunchedEffect(Unit) {
        galleryLauncher.launch("image/*")
    }
}
