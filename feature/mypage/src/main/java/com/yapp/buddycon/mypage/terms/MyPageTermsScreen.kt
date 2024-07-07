package com.yapp.buddycon.mypage.terms

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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

private const val TERMS_URL = "https://scarce-cartoon-27d.notion.site/2d0a87de00dc44bea41577c9af59b97f?pvs=4"

@Composable
fun MyPageTermsScreen(
    onBack: () -> Unit = {}
) {
    val webState = rememberWebViewState(url = TERMS_URL)
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
                state = webState,
                modifier = Modifier.fillMaxSize(),
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
