package com.yapp.buddycon.gifticon.edit

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.component.appbar.TopAppBarWithBack
import com.yapp.buddycon.designsystem.component.button.BuddyConButton
import com.yapp.buddycon.designsystem.component.button.GifticonDeleteButton
import com.yapp.buddycon.designsystem.component.custom.FullGifticonImage
import com.yapp.buddycon.designsystem.component.input.EssentialInputSelectDate
import com.yapp.buddycon.designsystem.component.input.EssentialInputSelectUsage
import com.yapp.buddycon.designsystem.component.input.EssentialInputText
import com.yapp.buddycon.designsystem.component.input.NoEssentialInputText
import com.yapp.buddycon.designsystem.component.modal.CalendarModalSheet
import com.yapp.buddycon.designsystem.component.modal.CategoryModalSheet
import com.yapp.buddycon.designsystem.component.utils.SpacerVertical
import com.yapp.buddycon.designsystem.theme.Black
import com.yapp.buddycon.designsystem.theme.Paddings
import com.yapp.buddycon.domain.model.type.GifticonStore
import com.yapp.buddycon.gifticon.detail.GifticonDetailViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun GifticonEditScreen(
    gifticonDetailViewModel: GifticonDetailViewModel,
    gifticonId: Int?,
) {
    checkNotNull(gifticonId)

    LaunchedEffect(Unit) {
        Log.d("MOATest", "gifticonDetailViewModel : ${gifticonDetailViewModel.hashCode()}, id : $gifticonId")
    }

    Scaffold(
        topBar = {
            TopAppBarWithBack(
                title = stringResource(R.string.gifticon),
            )
        },
        floatingActionButton = {
            BuddyConButton(
                modifier = Modifier
                    .padding(horizontal = Paddings.xlarge)
                    .fillMaxWidth(),
                text = stringResource(R.string.gifticon_edit_complete)
            ) {
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { paddingValues ->
        GifticonEditDetailContent(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            gifticonDetailViewModel = gifticonDetailViewModel
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GifticonEditDetailContent(
    modifier: Modifier = Modifier,
    gifticonDetailViewModel: GifticonDetailViewModel,
    gifticonEditViewModel: GifticonEditViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var isImageExpanded by remember { mutableStateOf(false) }

    val gifticonDetailModel by gifticonDetailViewModel.gifticonDetailModel.collectAsStateWithLifecycle()
    val editValueState by gifticonEditViewModel.editValueState.collectAsStateWithLifecycle()

    var isShowCalendarModal by remember { mutableStateOf(false) }
    var isShowCategoryModal by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        gifticonEditViewModel.initEditValueState(
            name = gifticonDetailModel.name,
            expireDate = gifticonDetailModel.expireDate,
            category = gifticonDetailModel.gifticonStore,
            memo = gifticonDetailModel.memo
        )
    }

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
            value = if (gifticonDetailModel.gifticonStore == GifticonStore.ETC) "" else gifticonDetailModel.gifticonStore.value,
            action = { isShowCategoryModal = true }
        )

        NoEssentialInputText(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.gifticon_memo),
            placeholder = stringResource(R.string.gifticon_memo_placeholder),
            value = gifticonDetailModel.memo,
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
                    // todo - delete gifticon
                }
            )
        }

        SpacerVertical(height = 125.dp)
    }
}