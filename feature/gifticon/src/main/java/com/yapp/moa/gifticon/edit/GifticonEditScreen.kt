package com.yapp.moa.gifticon.edit

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.yapp.moa.designsystem.component.appbar.TopAppBarWithBack
import com.yapp.moa.designsystem.component.button.BuddyConButton
import com.yapp.moa.designsystem.component.button.GifticonDeleteButton
import com.yapp.moa.designsystem.component.custom.FullGifticonImage
import com.yapp.moa.designsystem.component.dialog.ConfirmDialog
import com.yapp.moa.designsystem.component.dialog.DefaultDialog
import com.yapp.moa.designsystem.component.input.EssentialInputSelectDate
import com.yapp.moa.designsystem.component.input.EssentialInputSelectUsage
import com.yapp.moa.designsystem.component.input.EssentialInputText
import com.yapp.moa.designsystem.component.input.NoEssentialInputText
import com.yapp.moa.designsystem.component.modal.CalendarModalSheet
import com.yapp.moa.designsystem.component.modal.CategoryModalSheet
import com.yapp.moa.designsystem.component.utils.SpacerVertical
import com.yapp.moa.designsystem.theme.Black
import com.yapp.moa.designsystem.theme.Paddings
import com.yapp.moa.domain.model.type.GifticonStore
import com.yapp.moa.gifticon.available.base.HandleDataResult
import com.yapp.moa.gifticon.detail.GifticonDetailViewModel
import com.yapp.moa.designsystem.R

@Composable
fun GifticonEditScreen(
    gifticonDetailViewModel: GifticonDetailViewModel,
    gifticonId: Int?,
    onBack: () -> Unit,
    onBackToHome: () -> Unit,
    gifticonEditViewModel: GifticonEditViewModel = hiltViewModel()
) {
    checkNotNull(gifticonId)

    LaunchedEffect(Unit) {
        Log.d("MOATest", "gifticonDetailViewModel : ${gifticonDetailViewModel.hashCode()}, id : $gifticonId")
    }

    Scaffold(
        topBar = {
            TopAppBarWithBack(
                title = stringResource(R.string.gifticon),
                onBack = onBack
            )
        },
        floatingActionButton = {
            BuddyConButton(
                modifier = Modifier
                    .padding(horizontal = Paddings.xlarge)
                    .fillMaxWidth(),
                text = stringResource(R.string.gifticon_edit_complete)
            ) {
                gifticonEditViewModel.editGifticonDetail(gifticonId = gifticonId)
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        GifticonEditDetailContent(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            gifticonDetailViewModel = gifticonDetailViewModel,
            gifticonEditViewModel = gifticonEditViewModel,
            onEditSuccessResult = onBack,
            onGifticonDeleteSuccessResult = onBackToHome,
            gifticonId = gifticonId
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GifticonEditDetailContent(
    modifier: Modifier = Modifier,
    gifticonDetailViewModel: GifticonDetailViewModel,
    gifticonEditViewModel: GifticonEditViewModel,
    onEditSuccessResult: () -> Unit,
    onGifticonDeleteSuccessResult: () -> Unit,
    gifticonId: Int
) {
    val scrollState = rememberScrollState()
    var isImageExpanded by remember { mutableStateOf(false) }

    val gifticonDetailModel by gifticonDetailViewModel.gifticonDetailModel.collectAsStateWithLifecycle()
    val editValueState by gifticonEditViewModel.editValueState.collectAsStateWithLifecycle()

    var isShowCalendarModal by remember { mutableStateOf(false) }
    var isShowCategoryModal by remember { mutableStateOf(false) }

    var isShowSuccessDialog by remember { mutableStateOf(false) }
    var isShowGifticonDeleteOrNotDialog by remember { mutableStateOf(false) }
    var isShowGifticonDeleteCompleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        gifticonEditViewModel.initEditValueState(
            name = gifticonDetailModel.name,
            expireDate = gifticonDetailModel.expireDate,
            category = gifticonDetailModel.gifticonStore,
            memo = gifticonDetailModel.memo
        )
    }

    HandleDataResult(
        dataResultStateFlow = gifticonEditViewModel.editGifticonDetailDataResult,
        onSuccess = { isShowSuccessDialog = true },
        onFailure = { },
        onLoading = { }
    )

    HandleDataResult(
        dataResultStateFlow = gifticonEditViewModel.deleteGifticonDataResult,
        onSuccess = {
            isShowGifticonDeleteOrNotDialog = false
            isShowGifticonDeleteCompleteDialog = true
        },
        onFailure = { },
        onLoading = { }
    )

    if (isShowCalendarModal) {
        CalendarModalSheet(
            onSelectDate = { gifticonEditViewModel.setNewExpirationDate(it) },
            onDismiss = { isShowCalendarModal = false }
        )
    }

    if (isShowCategoryModal) {
        CategoryModalSheet(
            onSelectCategory = { gifticonEditViewModel.setNewCategory(it) },
            onDismiss = { isShowCategoryModal = false }
        )
    }

    if (isShowSuccessDialog) {
        ConfirmDialog(
            dialogTitle = stringResource(id = R.string.gifticon_edit_success_message),
            onClick = {
                onEditSuccessResult()
            }
        )
    }

    if (isShowGifticonDeleteOrNotDialog) {
        DefaultDialog(
            dialogTitle = stringResource(R.string.gifticon_delete_or_not_dialog_title),
            dismissText = stringResource(R.string.gifticon_delete_or_not_dialog_close),
            confirmText = stringResource(R.string.gifticon_delete_or_not_dialog_delete),
            onConfirm = {
                gifticonEditViewModel.deleteGifticon(gifticonId = gifticonId)
            },
            onDismissRequest = {
                isShowGifticonDeleteOrNotDialog = false
            }
        )
    }

    if (isShowGifticonDeleteCompleteDialog) {
        ConfirmDialog(
            dialogTitle = stringResource(id = R.string.gifticon_delete_complete_dialog_title),
            onClick = onGifticonDeleteSuccessResult,
            buttonText = stringResource(id = R.string.gifticon_delete_complete_dialog_back_to_home)
        )
    }

    FullGifticonImage(
        imageUri = gifticonDetailModel.imageUrl,
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
                model = gifticonDetailModel.imageUrl,
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
            value = editValueState.newName,
            onValueChange = { gifticonEditViewModel.setNewName(it) }
        )

        EssentialInputSelectDate(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.gifticon_expiration_date),
            placeholder = stringResource(R.string.gifticon_expiration_date_placeholder),
            value = editValueState.newExpireDate,
            action = { isShowCalendarModal = true }
        )

        EssentialInputSelectUsage(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.gifticon_usage),
            placeholder = stringResource(R.string.gifticon_usage_placeholder),
            value = if (editValueState.newGifticonStore == GifticonStore.OTHERS) "" else editValueState.newGifticonStore.value,
            action = { isShowCategoryModal = true }
        )

        NoEssentialInputText(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.gifticon_memo),
            placeholder = stringResource(R.string.gifticon_memo_placeholder),
            value = editValueState.newMemo,
            onValueChange = { gifticonEditViewModel.setNewMemo(it) }
        )

        SpacerVertical(height = 12.dp)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            GifticonDeleteButton(
                onClick = {
                    isShowGifticonDeleteOrNotDialog = true
                }
            )
        }

        SpacerVertical(height = 125.dp)
    }
}
