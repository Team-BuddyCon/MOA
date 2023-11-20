package com.yapp.buddycon.designsystem.component.modal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Grey90
import com.yapp.buddycon.designsystem.theme.Paddings

private val PlaceModalSheetHeight = 182.dp
private val PlaceModalSheetRadius = 24.dp
private val PlaceModalSheetHeaderHorizontalPadding = 7.dp
private val PlaceModalSheetHeaderIconSize = 60.dp
private val PlaceModalSheetButtonHeight = 54.dp
private val PlaceModalSheetButtonRadius = 12.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceModalSheet(
    sheetState: SheetState = rememberModalBottomSheetState(),
    location: String,
    distance: Float,
    onSearchLocation: (String) -> Unit = {},
    onCopyAddress: (String) -> Unit = {},
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier
            .fillMaxWidth()
            .height(PlaceModalSheetHeight),
        shape = RoundedCornerShape(
            topStart = PlaceModalSheetRadius,
            topEnd = PlaceModalSheetRadius
        ),
        containerColor = BuddyConTheme.colors.modalColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = Paddings.xlarge,
                    end = Paddings.xlarge,
                    bottom = Paddings.xlarge
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = PlaceModalSheetHeaderHorizontalPadding)
            ) {
                Text(
                    text = location,
                    style = BuddyConTheme.typography.subTitle
                )
                Row(
                    modifier = Modifier.padding(top = Paddings.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.modal_sheet_place_distance),
                        style = BuddyConTheme.typography.body04
                    )
                    Text(
                        text = String.format(
                            stringResource(R.string.modal_sheet_place_distance_format),
                            distance
                        ),
                        modifier = Modifier.padding(start = Paddings.xlarge),
                        style = BuddyConTheme.typography.body04
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Paddings.xlarge)
            ) {
                Button(
                    onClick = { onSearchLocation(location) },
                    modifier = Modifier
                        .weight(1f)
                        .height(PlaceModalSheetButtonHeight),
                    shape = RoundedCornerShape(PlaceModalSheetButtonRadius),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BuddyConTheme.colors.primary
                    )
                ) {
                    Text(
                        text = stringResource(R.string.modal_sheet_place_find_location),
                        style = BuddyConTheme.typography.subTitle.copy(
                            color = BuddyConTheme.colors.onPrimary
                        )
                    )
                }
                Button(
                    onClick = { onCopyAddress(location) },
                    modifier = Modifier
                        .padding(start = Paddings.medium)
                        .weight(1f)
                        .height(PlaceModalSheetButtonHeight),
                    shape = RoundedCornerShape(PlaceModalSheetButtonRadius),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Grey90
                    )
                ) {
                    Text(
                        text = stringResource(R.string.modal_sheet_place_copy_address),
                        style = BuddyConTheme.typography.subTitle.copy(
                            color = BuddyConTheme.colors.onPrimary
                        )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PlaceModalSheetPreview() {
    var isShowModal by remember { mutableStateOf(true) }
    BuddyConTheme {
        if (isShowModal) {
            PlaceModalSheet(
                location = "스타벅스 상봉점",
                distance = 0.1f,
                onDismiss = { isShowModal = false }
            )
        }
    }
}
