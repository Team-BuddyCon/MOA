package com.yapp.moa.mypage.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.moa.designsystem.component.appbar.TopAppBarWithBack
import com.yapp.moa.designsystem.component.button.ToggleButton
import com.yapp.moa.designsystem.component.utils.DividerHorizontal
import com.yapp.moa.designsystem.theme.BuddyConTheme
import com.yapp.moa.designsystem.theme.Grey60
import com.yapp.moa.designsystem.theme.Pink100
import com.yapp.moa.designsystem.R

@Composable
fun MyPageNotificationScreen(
    myPageNotificationViewModel: MyPageNotificationViewModel = hiltViewModel(),
    onBack: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBarWithBack(
                title = stringResource(R.string.setting_notification_title),
                onBack = onBack
            )
        }
    ) { paddingValues ->
        MyPageNotificationContent(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(BuddyConTheme.colors.background),
            myPageNotificationViewModel = myPageNotificationViewModel
        )
    }
}

@Composable
private fun MyPageNotificationContent(
    modifier: Modifier = Modifier,
    myPageNotificationViewModel: MyPageNotificationViewModel = hiltViewModel()
) {
    val notiSettings by myPageNotificationViewModel.notiSettings.collectAsStateWithLifecycle()
    Column(modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.setting_bar_notification),
                modifier = Modifier.weight(1f),
                style = BuddyConTheme.typography.subTitle
            )
            ToggleButton(
                isSelected = notiSettings.activated,
                onClick = {
                    myPageNotificationViewModel.updateNotificationSettings(
                        activated = it
                    )
                }
            )
        }
        DividerHorizontal(
            modifier = Modifier
                .padding(vertical = 4.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.setting_notification_guide),
                style = BuddyConTheme.typography.body03.copy(color = Grey60)
            )
            NotificationDay(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                text = stringResource(R.string.setting_notification_fourteenDaysBefore),
                isSelected = notiSettings.fourteenDaysBefore,
                onSelect = {
                    myPageNotificationViewModel.updateNotificationSettings(
                        fourteenDaysBefore = it
                    )
                }
            )
            NotificationDay(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                text = stringResource(R.string.setting_notification_sevenDaysBefore),
                isSelected = notiSettings.sevenDaysBefore,
                onSelect = {
                    myPageNotificationViewModel.updateNotificationSettings(
                        sevenDaysBefore = it
                    )
                }
            )
            NotificationDay(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                text = stringResource(R.string.setting_notification_threeDaysBefore),
                isSelected = notiSettings.threeDaysBefore,
                onSelect = {
                    myPageNotificationViewModel.updateNotificationSettings(
                        threeDaysBefore = it
                    )
                }
            )
            NotificationDay(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                text = stringResource(R.string.setting_notification_oneDayBefore),
                isSelected = notiSettings.oneDayBefore,
                onSelect = {
                    myPageNotificationViewModel.updateNotificationSettings(
                        oneDayBefore = it
                    )
                }
            )
            NotificationDay(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                text = stringResource(R.string.setting_notification_theDay),
                isSelected = notiSettings.theDay,
                onSelect = {
                    myPageNotificationViewModel.updateNotificationSettings(
                        theDay = it
                    )
                }
            )
        }
    }
}

@Composable
private fun NotificationDay(
    modifier: Modifier,
    text: String,
    isSelected: Boolean,
    onSelect: (Boolean) -> Unit
) {
    Row(
        modifier = modifier.clickable {
            onSelect(isSelected.not())
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            modifier = Modifier.weight(1f),
            style = BuddyConTheme.typography.subTitle.copy(
                color = if (isSelected) Pink100 else Grey60
            )
        )
        if (isSelected) {
            Icon(
                painter = painterResource(R.drawable.ic_check),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = Color.Unspecified
            )
        }
    }
}
