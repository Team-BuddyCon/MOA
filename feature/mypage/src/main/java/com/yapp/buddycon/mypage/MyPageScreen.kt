package com.yapp.buddycon.mypage

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yapp.buddycon.designsystem.component.appbar.TopAppBarForSetting
import com.yapp.buddycon.designsystem.component.setting.MainSettingBar
import com.yapp.buddycon.designsystem.component.utils.DividerHorizontal
import com.yapp.buddycon.designsystem.component.utils.SpacerHorizontal
import com.yapp.buddycon.designsystem.component.utils.SpacerVertical
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Grey90
import com.yapp.buddycon.designsystem.theme.White


const val TAG = "BuddyConTest"

@Composable
fun MyPageScreen() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBarForSetting(action = {})
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            UserName(userName = "버디콘") // todo - 실제 userName 전달

            SpacerVertical(height = 16.dp)

            UsedGifticonInfo(usedGifticon = 5) // todo - 실제 사용한 기프티콘 개수 전달

            SpacerVertical(height = 8.dp)

            MainSettingBar(
                mainTitle = stringResource(com.yapp.buddycon.designsystem.R.string.setting_bar_notification),
                subText = "ON",
                onSettingClick = { Log.d(TAG, "[알림] click") }
            )

            DividerHorizontal(modifier = Modifier.padding(horizontal = 16.dp))

            MainSettingBar(
                mainTitle = stringResource(com.yapp.buddycon.designsystem.R.string.setting_bar_inquiry),
                onSettingClick = { Log.d(TAG, "[문의하기] click") }
            )

            MainSettingBar(
                mainTitle = stringResource(com.yapp.buddycon.designsystem.R.string.setting_bar_version_info),
                subText = "1.1", // todo - version 정보
                onSettingClick = { Log.d(TAG, "[버전 정보] click") }
            )

            MainSettingBar(
                mainTitle = stringResource(com.yapp.buddycon.designsystem.R.string.setting_bar_policy),
                onSettingClick = { Log.d(TAG, "[약관 및 정책] click") }
            )

            MainSettingBar(
                mainTitle = stringResource(com.yapp.buddycon.designsystem.R.string.setting_bar_open_source_license),
                onSettingClick = { Log.d(TAG, "[오픈소스 라이센스] click") }
            )

            DividerHorizontal(modifier = Modifier.padding(horizontal = 16.dp))

            MainSettingBar(
                mainTitle = stringResource(com.yapp.buddycon.designsystem.R.string.setting_bar_logout),
                onSettingClick = { Log.d(TAG, "[로그아웃] click") }
            )

            MainSettingBar(
                mainTitle = stringResource(com.yapp.buddycon.designsystem.R.string.setting_bar_withdrawal),
                onSettingClick = { Log.d(TAG, "[탈퇴하기] click") }
            )
        }
    }
}

@Composable
fun UserName(userName: String) {
    Text(
        text = "안녕하세요,\n${userName}님!",
        style = BuddyConTheme.typography.title02,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
fun UsedGifticonInfo(usedGifticon: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(
                color = Grey90,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(
                    color = White,
                    shape = CircleShape
                )
        ) {
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.Center),
                painter = painterResource(id = com.yapp.buddycon.designsystem.R.drawable.ic_mypage_used_gifticon),
                contentDescription = "used gifticon"
            )
        }

        SpacerHorizontal(width = 16.dp)

        Column {
            Text(
                text = "사용한 기프티콘",
                style = BuddyConTheme.typography.body03,
                color = White
            )
            Text(
                text = "${usedGifticon}개",
                style = BuddyConTheme.typography.subTitle,
                color = White
            )
        }
    }
}
