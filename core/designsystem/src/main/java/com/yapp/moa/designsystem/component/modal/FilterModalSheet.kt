package com.yapp.moa.designsystem.component.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import com.yapp.moa.designsystem.theme.BuddyConTheme
import com.yapp.moa.designsystem.theme.Grey30
import com.yapp.moa.designsystem.theme.Grey40
import com.yapp.moa.designsystem.theme.Grey50
import com.yapp.moa.designsystem.theme.Pink100
import com.yapp.moa.designsystem.theme.White
import com.yapp.moa.domain.model.type.SortType
import com.yapp.moa.designsystem.R

private val FilterModalSheetHeight = 228.dp
private val FilterModelSheetRadius = 24.dp
private val FilterModalItemHorizontalPadding = 16.dp
private val FilterModalItemIconSize = 18.dp
private val FilterModalItemHeight = 64.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterModalSheet(
    sheetState: SheetState = rememberModalBottomSheetState(),
    sortType: SortType,
    onChangeSortType: (SortType) -> Unit = {},
    onDismiss: () -> Unit
) {
    BottomSheetDialog(
        onDismissRequest = onDismiss,
        properties = BottomSheetDialogProperties()
    ) {
        Column(
            Modifier.fillMaxWidth()
                .background(White, RoundedCornerShape(topStart = FilterModelSheetRadius, topEnd = FilterModelSheetRadius))
                .height(FilterModalSheetHeight)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp),
                contentAlignment = Alignment.Center
            ) {
                Spacer(
                    modifier = Modifier
                        .size(32.dp, 4.dp)
                        .background(Grey40, RoundedCornerShape(100.dp))
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(FilterModalItemHeight)
                    .clickable { onChangeSortType(SortType.EXPIRATION_DATE) }
                    .padding(horizontal = FilterModalItemHorizontalPadding)
            ) {
                Text(
                    text = SortType.EXPIRATION_DATE.value,
                    modifier = Modifier.weight(1f),
                    style = BuddyConTheme.typography.body02.copy(
                        color = if (sortType == SortType.EXPIRATION_DATE) Pink100 else Grey50
                    )
                )
                if (sortType == SortType.EXPIRATION_DATE) {
                    Icon(
                        painter = painterResource(R.drawable.ic_check),
                        contentDescription = SortType.EXPIRATION_DATE.value,
                        modifier = Modifier.size(FilterModalItemIconSize),
                        tint = Pink100
                    )
                }
            }
            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Grey30
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(FilterModalItemHeight)
                    .clickable { onChangeSortType(SortType.REGISTRATION) }
                    .padding(horizontal = FilterModalItemHorizontalPadding)
            ) {
                Text(
                    text = SortType.REGISTRATION.value,
                    modifier = Modifier.weight(1f),
                    style = BuddyConTheme.typography.body02.copy(
                        color = if (sortType == SortType.REGISTRATION) Pink100 else Grey50
                    )
                )
                if (sortType == SortType.REGISTRATION) {
                    Icon(
                        painter = painterResource(R.drawable.ic_check),
                        contentDescription = SortType.REGISTRATION.value,
                        modifier = Modifier.size(FilterModalItemIconSize),
                        tint = Pink100
                    )
                }
            }
            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Grey30
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(FilterModalItemHeight)
                    .clickable { onChangeSortType(SortType.NAME) }
                    .padding(horizontal = FilterModalItemHorizontalPadding)
            ) {
                Text(
                    text = SortType.NAME.value,
                    modifier = Modifier.weight(1f),
                    style = BuddyConTheme.typography.body02.copy(
                        color = if (sortType == SortType.NAME) Pink100 else Grey50
                    )
                )
                if (sortType == SortType.NAME) {
                    Icon(
                        painter = painterResource(R.drawable.ic_check),
                        contentDescription = SortType.NAME.value,
                        modifier = Modifier.size(FilterModalItemIconSize),
                        tint = Pink100
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun FilterModalSheetPreview() {
    var isShowModal by remember { mutableStateOf(true) }
    var sortTypeState = remember { mutableStateOf(SortType.EXPIRATION_DATE) }
    BuddyConTheme {
        if (isShowModal) {
            FilterModalSheet(
                sortType = sortTypeState.value,
                onChangeSortType = { sortTypeState.value = it },
                onDismiss = { isShowModal = false }
            )
        }
    }
}
