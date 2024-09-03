package com.yapp.moa.mypage

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.yapp.moa.designsystem.R
import com.yapp.moa.designsystem.component.appbar.TopAppBarForSetting
import com.yapp.moa.designsystem.component.dialog.ConfirmDialog
import com.yapp.moa.designsystem.component.dialog.DefaultDialog
import com.yapp.moa.designsystem.component.setting.MainSettingBar
import com.yapp.moa.designsystem.component.utils.DividerHorizontal
import com.yapp.moa.designsystem.component.utils.SpacerHorizontal
import com.yapp.moa.designsystem.component.utils.SpacerVertical
import com.yapp.moa.designsystem.theme.BuddyConTheme
import com.yapp.moa.designsystem.theme.Grey90
import com.yapp.moa.designsystem.theme.Paddings
import com.yapp.moa.designsystem.theme.White
import com.yapp.moa.utility.getVersionName

const val TAG = "BuddyConTest"

@Composable
fun MyPageScreen(
    myPageViewModel: MyPageViewModel = hiltViewModel(),
    onNavigateToUsedGifticon: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
    onNavigateToDeleteMember: () -> Unit = {},
    onNavigateToTerms: () -> Unit = {},
    onNavigateToVersion: () -> Unit = {},
    onNavigateToNotification: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val userName by myPageViewModel.userName.collectAsStateWithLifecycle()
    val usedGifticonCount by myPageViewModel.usedGifticonCount.collectAsStateWithLifecycle()
    var notDevelopedPopup by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        myPageViewModel.getNotificationSettings()
    }

    if (notDevelopedPopup) {
        ConfirmDialog(
            dialogTitle = stringResource(R.string.not_developed_feature_popup_title),
            dialogContent = stringResource(R.string.not_developed_feature_popup_description),
            onClick = { notDevelopedPopup = false },
            onDismissRequest = { notDevelopedPopup = false }
        )
    }

    Scaffold(
        topBar = {
            TopAppBarForSetting(action = {
                notDevelopedPopup = true
            })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            UserName(userName = userName)

            SpacerVertical(height = 16.dp)

            UsedGifticonInfo(
                usedGifticon = usedGifticonCount,
                onUsedGiftionClick = onNavigateToUsedGifticon
            )

            SpacerVertical(height = 8.dp)

            MyPageSettingBars(
                myPageViewModel = myPageViewModel,
                onNavigateToLogin = onNavigateToLogin,
                onNavigateToDeleteMember = onNavigateToDeleteMember,
                onNavigateToTerms = onNavigateToTerms,
                onNavigateToVersion = onNavigateToVersion,
                onNavigateToNotification = {
                    // TODO 알림으로 이동
                    notDevelopedPopup = true
                }
            )
        }
    }
}

@Composable
private fun UserName(userName: String) {
    Text(
        text = "안녕하세요,\n${userName}님!",
        style = BuddyConTheme.typography.title02,
        modifier = Modifier.padding(horizontal = Paddings.xlarge)
    )
}

@Composable
private fun UsedGifticonInfo(
    usedGifticon: Int,
    onUsedGiftionClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(
                color = Grey90,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp)
            .clickable { onUsedGiftionClick() },
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
                painter = painterResource(id = R.drawable.ic_mypage_used_gifticon),
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

@Composable
private fun MyPageSettingBars(
    myPageViewModel: MyPageViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit = {},
    onNavigateToDeleteMember: () -> Unit = {},
    onNavigateToTerms: () -> Unit = {},
    onNavigateToVersion: () -> Unit = {},
    onNavigateToNotification: () -> Unit = {}
) {
    val logoutEvent by myPageViewModel.logoutEvent.collectAsStateWithLifecycle()
    val isActiveNotification by myPageViewModel.isActiveNotification.collectAsStateWithLifecycle()
    var showLogoutPopup by remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (showLogoutPopup) {
        DefaultDialog(
            dialogTitle = stringResource(R.string.setting_logout_popup_title),
            dismissText = stringResource(R.string.setting_logout_popup_dismiss),
            confirmText = stringResource(R.string.setting_bar_logout),
            dialogContent = stringResource(R.string.setting_logout_popup_content),
            onConfirm = {
                showLogoutPopup = false
                myPageViewModel.fetchLogout()
            },
            onDismissRequest = {
                showLogoutPopup = false
            }
        )
    }

    if (logoutEvent) {
        ConfirmDialog(
            dialogTitle = stringResource(R.string.setting_logout_success),
            onClick = { onNavigateToLogin() }
        )
    }

    MainSettingBar(
        mainTitle = stringResource(R.string.setting_bar_notification),
        subText = if (isActiveNotification) "ON" else "OFF",
        onSettingClick = {
            onNavigateToNotification()
        }
    )

    DividerHorizontal(modifier = Modifier.padding(horizontal = 16.dp))

    MainSettingBar(
        mainTitle = stringResource(R.string.setting_bar_inquiry),
        onSettingClick = {
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.setData(Uri.parse("mailto:"))

            val intent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_EMAIL, arrayOf(context.getString(R.string.mypage_moa_email)))
                putExtra(Intent.EXTRA_SUBJECT, "문의하기")
                putExtra(Intent.EXTRA_TEXT, "")
                selector = emailIntent
            }

            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(Intent.createChooser(intent, "Send Email"))
            }
        }
    )

    MainSettingBar(
        mainTitle = stringResource(R.string.setting_bar_version_info),
        subText = context.getVersionName(),
        onSettingClick = {
            onNavigateToVersion()
        }
    )

    MainSettingBar(
        mainTitle = stringResource(R.string.setting_bar_policy),
        onSettingClick = {
            onNavigateToTerms()
        }
    )

    MainSettingBar(
        mainTitle = stringResource(R.string.setting_bar_open_source_license),
        onSettingClick = {
            context.startActivity(
                Intent(context, OssLicensesMenuActivity::class.java)
            )
        }
    )

    DividerHorizontal(modifier = Modifier.padding(horizontal = 16.dp))

    MainSettingBar(
        mainTitle = stringResource(R.string.setting_bar_logout),
        onSettingClick = {
            showLogoutPopup = true
        }
    )

    MainSettingBar(
        mainTitle = stringResource(R.string.setting_bar_withdrawal),
        onSettingClick = {
            onNavigateToDeleteMember()
        }
    )
}
