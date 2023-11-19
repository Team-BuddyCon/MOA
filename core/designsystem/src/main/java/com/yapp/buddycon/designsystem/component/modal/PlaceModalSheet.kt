package com.yapp.buddycon.designsystem.component.modal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Grey90
import com.yapp.buddycon.designsystem.theme.Paddings

private val PlaceModalSheetHeight = 200.dp
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
    painter: Painter,
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = PlaceModalSheetHeaderHorizontalPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = location,
                    modifier = Modifier.weight(1f),
                    style = BuddyConTheme.typography.subTitle
                )
                Icon(
                    painter = painter,
                    contentDescription = location,
                    modifier = Modifier.size(PlaceModalSheetHeaderIconSize),
                    tint = Color.Unspecified
                )
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