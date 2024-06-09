package com.yapp.buddycon.mypage.terms

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.component.appbar.TopAppBarWithBack
import com.yapp.buddycon.designsystem.theme.BuddyConTheme

private const val SERVICE_TERMS_URL = "https://scarce-cartoon-27d.notion.site/e09da35361e142b7936c12e38396475e"
private const val PRIVACY_INFORMATION_TERMS_URL = "https://scarce-cartoon-27d.notion.site/c4e5ff54f9bd434e971a2631d122252c?pvs=4"

@Composable
fun MyPageTermsScreen(
    onBack: () -> Unit = {}
) {
    val serviceWebViewState = rememberWebViewState(url = SERVICE_TERMS_URL)
    val privacyWebViewState = rememberWebViewState(url = PRIVACY_INFORMATION_TERMS_URL)
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBarWithBack(
                title = stringResource(R.string.setting_terms_title),
                onBack = onBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(BuddyConTheme.colors.background)
        ) {
            WebView(
                state = serviceWebViewState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, true),
                onCreated = {
                    it.settings.javaScriptEnabled = true
                    it.settings.domStorageEnabled = true
                }
            )
            WebView(
                state = privacyWebViewState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, true),
                onCreated = {
                    it.settings.javaScriptEnabled = true
                    it.settings.domStorageEnabled = true
                }
            )
        }
    }

    BackHandler {
        onBack()
    }
}
