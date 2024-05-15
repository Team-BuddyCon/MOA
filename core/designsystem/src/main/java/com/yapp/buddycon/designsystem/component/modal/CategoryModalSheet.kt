package com.yapp.buddycon.designsystem.component.modal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.component.utils.DividerHorizontal
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Grey30
import com.yapp.buddycon.designsystem.theme.Grey40
import com.yapp.buddycon.designsystem.theme.Grey60
import com.yapp.buddycon.designsystem.theme.Grey70
import com.yapp.buddycon.designsystem.theme.Paddings
import com.yapp.buddycon.designsystem.theme.White
import com.yapp.buddycon.domain.model.type.GifticonStore

private val CategoryModalSheetHeight = 498.dp
private val OthersCategoryModalSheetHeight = 400.dp
private val CategoryModalSheetRadius = 24.dp
private val CategoryModalSheetDragHandleHeight = 24.dp
private val CategoryModalSheetDragHandleTopPadding = 16.dp
private val CategoryModalSheetItemTopPadding = 51.dp
private val CategoryModalSheetItemBottomPadding = 37.dp
private val CategoryModalSheetItemImageSize = 68.dp
private val CateogryModalSheetCloseDescription = "Close"

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CategoryModalSheet(
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onSelectCategory: (GifticonStore) -> Unit = {},
    onSubmitOthers: (String) -> Unit = {},
    onDismiss: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var clickedOthers by remember { mutableStateOf(false) }
    var store by remember { mutableStateOf("") }
    BottomSheetDialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(White, RoundedCornerShape(topStart = CategoryModalSheetRadius, topEnd = CategoryModalSheetRadius))
                .height(
                    if (clickedOthers) {
                        OthersCategoryModalSheetHeight
                    } else {
                        CategoryModalSheetHeight
                    }
                )
        ) {
            Box(
                modifier = Modifier
                    .padding(top = CategoryModalSheetDragHandleTopPadding)
                    .padding(horizontal = Paddings.xlarge)
                    .fillMaxWidth()
                    .height(CategoryModalSheetDragHandleHeight),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(
                        if (clickedOthers) {
                            R.string.others
                        } else {
                            R.string.modal_sheet_category_title
                        }
                    ),
                    style = BuddyConTheme.typography.subTitle
                )
                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = CateogryModalSheetCloseDescription,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .fillMaxHeight()
                        .aspectRatio(1f)
                        .clickable { onDismiss() },
                    tint = Color.Unspecified
                )
            }
            if (clickedOthers) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_others_gifticon),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(top = 40.dp)
                            .size(40.dp),
                        tint = Color.Unspecified
                    )
                    Text(
                        text = stringResource(R.string.gifticon_others_register_title),
                        style = BuddyConTheme.typography.body03.copy(
                            color = Grey70
                        )
                    )
                    BasicTextField(
                        value = store,
                        onValueChange = { store = it },
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .fillMaxWidth()
                            .height(22.dp),
                        textStyle = BuddyConTheme.typography.body01,
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                            }
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        maxLines = 1
                    ) { innerTextField ->
                        if (store.isEmpty()) {
                            Text(
                                text = stringResource(R.string.gifticon_others_register_placeholder),
                                style = BuddyConTheme.typography.body01.copy(
                                    color = Grey40
                                )
                            )
                        }
                        innerTextField()
                    }
                    DividerHorizontal(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                    )
                    Text(
                        text = stringResource(R.string.gifticon_others_register_guide_message),
                        modifier = Modifier.padding(top = 8.dp),
                        style = BuddyConTheme.typography.body05.copy(
                            color = Grey60
                        )
                    )
                    Row(
                        modifier = Modifier
                            .padding(top = 80.dp)
                            .fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                clickedOthers = false
                                store = ""
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(54.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Grey30
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.previous),
                                style = BuddyConTheme.typography.subTitle.copy(
                                    color = Grey70
                                )
                            )
                        }
                        Button(
                            onClick = {
                                onSubmitOthers(store)
                                onSelectCategory(GifticonStore.OTHERS)
                                onDismiss()
                            },
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .weight(1f)
                                .height(54.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BuddyConTheme.colors.primary
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.write_complete),
                                style = BuddyConTheme.typography.subTitle.copy(
                                    color = BuddyConTheme.colors.onPrimary
                                )
                            )
                        }
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(
                        start = Paddings.xlarge,
                        end = Paddings.xlarge,
                        top = CategoryModalSheetItemTopPadding,
                        bottom = CategoryModalSheetItemBottomPadding
                    ),
                    verticalArrangement = Arrangement.spacedBy(Paddings.xextra),
                    horizontalArrangement = Arrangement.spacedBy(Paddings.xlarge)
                ) {
                    items(GifticonStore.values().copyOfRange(1, GifticonStore.values().size - 1)) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    if (it == GifticonStore.OTHERS) {
                                        clickedOthers = true
                                    } else {
                                        onSelectCategory(it)
                                        onDismiss()
                                    }
                                },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = it.logo()),
                                contentDescription = it.value,
                                modifier = Modifier.size(CategoryModalSheetItemImageSize),
                                contentScale = ContentScale.FillBounds
                            )
                            Text(
                                text = it.value,
                                modifier = Modifier.padding(top = Paddings.xlarge),
                                style = BuddyConTheme.typography.body03.copy(
                                    fontSize = 13.sp,
                                    lineHeight = (18.2).sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

fun GifticonStore.logo(): Int = when (this) {
    GifticonStore.STARBUCKS -> R.drawable.ic_starbucks
    GifticonStore.TWOSOME_PLACE -> R.drawable.ic_twosome
    GifticonStore.ANGELINUS -> R.drawable.ic_angelinus
    GifticonStore.MEGA_COFFEE -> R.drawable.ic_mega_coffee
    GifticonStore.COFFEE_BEAN -> R.drawable.ic_coffee_bean
    GifticonStore.GONG_CHA -> R.drawable.ic_gongcha
    GifticonStore.BASKIN_ROBBINS -> R.drawable.ic_baskinrobbins
    GifticonStore.MACDONALD -> R.drawable.ic_mcdonald
    GifticonStore.GS25 -> R.drawable.ic_gs25
    GifticonStore.CU -> R.drawable.ic_cu
    GifticonStore.OTHERS -> R.drawable.ic_etc
    else -> throw IllegalStateException()
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun CategoryModalSheetPreview() {
    var isShowModal by remember { mutableStateOf(true) }
    BuddyConTheme {
        if (isShowModal) {
            CategoryModalSheet {
                isShowModal = false
            }
        }
    }
}
