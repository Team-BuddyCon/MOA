package com.yapp.buddycon.designsystem.component.modal

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Paddings
import com.yapp.buddycon.domain.model.type.GifticonCategory

private val CategoryModalSheetHeight = 498.dp
private val CategoryModalSheetRadius = 24.dp
private val CategoryModalSheetDragHandleHeight = 24.dp
private val CategoryModalSheetDragHandleTopPadding = 17.dp
private val CategoryModalSheetItemTopPadding = 25.dp
private val CategoryModalSheetItemBottomPadding = 35.dp
private val CategoryModalSheetItemImageSize = 68.dp
private val CateogryModalSheetCloseDescription = "Close"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryModalSheet(
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onSelectCategory: (GifticonCategory) -> Unit = {},
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier
            .fillMaxWidth()
            .height(CategoryModalSheetHeight),
        shape = RoundedCornerShape(
            topStart = CategoryModalSheetRadius,
            topEnd = CategoryModalSheetRadius
        ),
        containerColor = BuddyConTheme.colors.modalColor,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = CategoryModalSheetDragHandleTopPadding)
                    .padding(horizontal = Paddings.xlarge)
                    .fillMaxWidth()
                    .height(CategoryModalSheetDragHandleHeight),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.modal_sheet_category_title),
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
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Paddings.xlarge)
                .padding(
                    top = CategoryModalSheetItemTopPadding,
                    bottom = CategoryModalSheetItemBottomPadding
                ),
            verticalArrangement = Arrangement.spacedBy(Paddings.xextra),
            horizontalArrangement = Arrangement.spacedBy(Paddings.xlarge)
        ) {
            items(GifticonCategory.values().copyOfRange(1, GifticonCategory.values().size - 1)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onSelectCategory(it)
                            onDismiss()
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

private fun GifticonCategory.logo(): Int = when (this) {
    GifticonCategory.STARBUCKS -> R.drawable.ic_starbucks
    GifticonCategory.TWOSOME_PLACE -> R.drawable.ic_twosome
    GifticonCategory.ANGELINUS -> R.drawable.ic_angelinus
    GifticonCategory.MEGA_COFFEE -> R.drawable.ic_mega_coffee
    GifticonCategory.COFFEE_BEAN -> R.drawable.ic_coffee_bean
    GifticonCategory.GONG_CHA -> R.drawable.ic_gongcha
    GifticonCategory.BASKINROBBINS -> R.drawable.ic_baskinrobbins
    GifticonCategory.MCDONALD -> R.drawable.ic_mcdonald
    GifticonCategory.GS25 -> R.drawable.ic_gs25
    GifticonCategory.CU -> R.drawable.ic_cu
    GifticonCategory.ETC -> R.drawable.ic_etc
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
