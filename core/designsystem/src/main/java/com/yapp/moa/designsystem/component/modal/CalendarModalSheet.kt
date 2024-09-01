package com.yapp.moa.designsystem.component.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.yapp.moa.designsystem.theme.BuddyConTheme
import com.yapp.moa.designsystem.theme.Paddings
import com.yapp.moa.designsystem.theme.White
import com.yapp.moa.designsystem.R

private val CalendarModalSheetHeight = 550.dp
private val CalendarModalSheetRadius = 24.dp
private val CalendarModalSheetDragHandleTopPadding = 17.dp
private val CalendarModalSheetDragHandleHeight = 24.dp
private val CalendarModalSheetDragHandleCloseIconDescription = "Close"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarModalSheet(
    sheetState: SheetState = rememberModalBottomSheetState(),
    onSelectDate: (Long) -> Unit = {},
    onDismiss: () -> Unit
) {
    val dateState = rememberDatePickerState()
    BottomSheetDialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .background(White, RoundedCornerShape(topStart = CalendarModalSheetRadius, topEnd = CalendarModalSheetRadius))
                .padding(bottom = CalendarModalSheetDragHandleTopPadding)
                .wrapContentHeight()
        ) {
            Box(
                modifier = Modifier
                    .padding(top = CalendarModalSheetDragHandleTopPadding)
                    .padding(horizontal = Paddings.xlarge)
                    .fillMaxWidth()
                    .height(CalendarModalSheetDragHandleHeight),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.modal_sheet_calendar_title),
                    style = BuddyConTheme.typography.subTitle
                )
                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = CalendarModalSheetDragHandleCloseIconDescription,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .fillMaxHeight()
                        .aspectRatio(1f)
                        .clickable {
                            dateState.selectedDateMillis?.let(onSelectDate)
                            onDismiss()
                        },
                    tint = Color.Unspecified
                )
            }
            DatePicker(state = dateState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun CalendarModalSheet() {
    var isShowModal by remember { mutableStateOf(true) }
    BuddyConTheme {
        if (isShowModal) {
            CalendarModalSheet {
                isShowModal = false
            }
        }
    }
}
