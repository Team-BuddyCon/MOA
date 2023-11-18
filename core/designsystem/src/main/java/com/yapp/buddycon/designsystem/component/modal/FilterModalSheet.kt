package com.yapp.buddycon.designsystem.component.modal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Grey30
import com.yapp.buddycon.designsystem.theme.Grey50
import com.yapp.buddycon.designsystem.theme.Pink100
import com.yapp.buddycon.domain.model.type.SortType

private val FILTER_MODAL_HEIGHT = 228.dp
private val FILTER_MODAL_ITEM_HORIZONTAL_PADDING = 16.dp
private val FILTER_MODAL_ITEM_ICON_SIZE = 18.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterModalSheet(
    sheetState: SheetState = rememberModalBottomSheetState(),
    sortType: SortType,
    onChangeSortType: (SortType) -> Unit = {},
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier
            .fillMaxWidth()
            .height(FILTER_MODAL_HEIGHT),
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        containerColor = BuddyConTheme.colors.modalColor
    ) {
        Column(Modifier.fillMaxSize()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clickable { onChangeSortType(SortType.EXPIRATION_DATE) }
                    .padding(horizontal = FILTER_MODAL_ITEM_HORIZONTAL_PADDING)
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
                        modifier = Modifier.size(FILTER_MODAL_ITEM_ICON_SIZE),
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
                    .weight(1f)
                    .fillMaxWidth()
                    .clickable { onChangeSortType(SortType.REGISTRATION) }
                    .padding(horizontal = FILTER_MODAL_ITEM_HORIZONTAL_PADDING)
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
                        modifier = Modifier.size(FILTER_MODAL_ITEM_ICON_SIZE),
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
                    .weight(1f)
                    .fillMaxWidth()
                    .clickable { onChangeSortType(SortType.NAME) }
                    .padding(horizontal = FILTER_MODAL_ITEM_HORIZONTAL_PADDING)
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
                        modifier = Modifier.size(FILTER_MODAL_ITEM_ICON_SIZE),
                        tint = Pink100
                    )
                }
            }
        }
    }
}
