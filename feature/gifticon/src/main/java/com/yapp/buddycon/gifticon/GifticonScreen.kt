package com.yapp.buddycon.gifticon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.component.appbar.TopAppBarWithNotification
import com.yapp.buddycon.designsystem.component.button.FloatingActionButton
import com.yapp.buddycon.designsystem.theme.BuddyConTheme

@Composable
fun GifticonScreeen(
    onNavigateToRegister: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBarWithNotification(stringResource(R.string.gifticon))
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = { FloatingActionButton(onNavigateToRegister) }
    ) { paddingValues ->
        GifticonContent(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(BuddyConTheme.colors.background)
        )
    }
}

@Composable
private fun GifticonContent(
    modifier: Modifier = Modifier
) {
    Column(modifier) {
    }
}
