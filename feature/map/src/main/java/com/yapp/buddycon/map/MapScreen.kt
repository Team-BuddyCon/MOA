package com.yapp.buddycon.map

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.component.appbar.TopAppBarWithNotification
import com.yapp.buddycon.designsystem.component.modal.GifticonInfoModalSheetContent
import com.yapp.buddycon.designsystem.theme.BuddyConTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen() {
    BottomSheetScaffold(
        sheetContent = {
            GifticonInfoModalSheetContent(
                modifier = Modifier.padding(bottom = 11.dp),
                countOfUsableGifticon = 12,
                countOfImminetGifticon = 1
            )
        },
        scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = SheetState(
                skipPartiallyExpanded = false,
                initialValue = SheetValue.Expanded,
                skipHiddenState = true
            )
        ),
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetContainerColor = BuddyConTheme.colors.background,
        sheetShadowElevation = 33.dp,
        topBar = {
            TopAppBarWithNotification(
                title = stringResource(R.string.map)
            )
        }
    ) {
    }
}
