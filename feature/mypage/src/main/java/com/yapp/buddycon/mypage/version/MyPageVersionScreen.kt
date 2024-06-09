package com.yapp.buddycon.mypage.version

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.component.appbar.TopAppBarWithBack
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.mypage.BuildConfig
import com.yapp.buddycon.utility.getVersionName

@Composable
fun MyPageVersionScreen(
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBarWithBack(
                title = stringResource(R.string.setting_version_information_title),
                onBack = onBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.run {
                padding(paddingValues)
                    .fillMaxSize()
                    .background(BuddyConTheme.colors.background)
                    .padding(top = 19.dp)
                    .padding(horizontal = 16.dp)
            }
        ) {
            Text(
                text = stringResource(R.string.setting_version_information_title),
                style = BuddyConTheme.typography.title02
            )
            Text(
                text = String.format(
                    stringResource(R.string.setting_version_information_format),
                    context.getVersionName(),
                    BuildConfig.VERSION_DATE
                ),
                modifier = Modifier.padding(top = 16.dp),
                style = BuddyConTheme.typography.body01
            )
        }
    }
}
