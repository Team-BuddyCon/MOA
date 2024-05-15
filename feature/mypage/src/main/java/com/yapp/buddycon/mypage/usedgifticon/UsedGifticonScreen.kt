package com.yapp.buddycon.mypage.usedgifticon

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.component.appbar.TopAppBarWithBack
import com.yapp.buddycon.designsystem.component.utils.SpacerVertical
import com.yapp.buddycon.designsystem.theme.BuddyConTheme

@Composable
fun UsedGifticon(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBarWithBack(
                title = stringResource(R.string.used_gifticon_screen_appbar_title),
                onBack = onBack
            )
        }
    ) { paddingValues ->
        UsedGifticonContent(
            modifier = Modifier.padding(paddingValues = paddingValues)
        )
    }
}

@Composable
private fun UsedGifticonContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        SpacerVertical(height = 24.dp)
        UsedGiftionCount(1) /***/
    }
}

@Composable
private fun UsedGiftionCount(count: Int) {
    Text(
        text = "${count}개 사용했어요!",
        style = BuddyConTheme.typography.title02
    )
}