package com.yapp.buddycon.startup.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.component.appbar.TopAppBarWithBack
import com.yapp.buddycon.designsystem.component.button.BuddyConButton
import com.yapp.buddycon.designsystem.component.utils.DividerHorizontal
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Grey40
import com.yapp.buddycon.designsystem.theme.Grey60
import com.yapp.buddycon.designsystem.theme.Paddings

@Composable
fun SignUpScreen(
    onNavigateToWelcome: () -> Unit = {},
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBarWithBack(
                title = stringResource(R.string.signup),
                onBack = onBack
            )
        }
    ) { paddingValues ->
        SignUpContent(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(BuddyConTheme.colors.background),
            onNavigateToWelcome = onNavigateToWelcome
        )
    }
}

@Composable
private fun SignUpContent(
    modifier: Modifier = Modifier,
    onNavigateToWelcome: () -> Unit = {},
) {
    var signUpTermsState by remember { mutableStateOf(SignUpTermsState()) }
    Column(
        modifier = modifier
            .padding(top = 19.dp, bottom = Paddings.xlarge)
            .padding(horizontal = Paddings.xlarge)
    ) {
        Text(
            text = stringResource(R.string.signup_terms_title),
            style = BuddyConTheme.typography.title02
        )
        SignUpTermsContent(
            modifier = Modifier
                .padding(top = 31.dp)
                .fillMaxWidth(),
            isChecked = signUpTermsState.isAllChecked(),
            text = stringResource(R.string.signup_all_terms_agree),
            onCheck = { check ->
                signUpTermsState = if (check) {
                    signUpTermsState.copy(termsOfUse = true, privacyPolicy = true)
                } else {
                    signUpTermsState.copy(termsOfUse = false, privacyPolicy = false)
                }
            }
        )
        DividerHorizontal(
            modifier = Modifier.padding(vertical = Paddings.xlarge)
        )
        SignUpTermsContent(
            modifier = Modifier.fillMaxWidth(),
            isChecked = signUpTermsState.termsOfUse,
            text = stringResource(R.string.signup_terms_of_use_agree),
            hasMore = true,
            onCheck = { signUpTermsState = signUpTermsState.copy(termsOfUse = it) }
        )
        SignUpTermsContent(
            modifier = Modifier
                .padding(top = Paddings.xlarge)
                .fillMaxWidth(),
            isChecked = signUpTermsState.privacyPolicy,
            text = stringResource(R.string.signup_privacy_policy_agree),
            hasMore = true,
            onCheck = { signUpTermsState = signUpTermsState.copy(privacyPolicy = it) }
        )
        Spacer(Modifier.weight(1f))
        BuddyConButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.signup_complete),
            enabled = signUpTermsState.isEssentialChecked(),
            containerColor = if (signUpTermsState.isEssentialChecked()) {
                BuddyConTheme.colors.primary
            } else {
                Grey40
            },
            contentColor = if (signUpTermsState.isEssentialChecked()) {
                BuddyConTheme.colors.onPrimary
            } else {
                Grey60
            }
        ) {
            onNavigateToWelcome()
        }
    }
}

@Composable
private fun SignUpTermsContent(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    text: String,
    hasMore: Boolean = false,
    onCheck: (Boolean) -> Unit = {},
    onClickHasMore: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(if (isChecked) R.drawable.ic_circle_check_sel else R.drawable.ic_circle_check),
            contentDescription = text,
            modifier = Modifier
                .size(24.dp)
                .clickable { onCheck(isChecked.not()) },
            tint = Color.Unspecified
        )
        Text(
            text = text,
            modifier = Modifier.padding(start = Paddings.medium),
            style = BuddyConTheme.typography.body01.copy(color = Grey60)
        )
        Spacer(Modifier.weight(1f))
        if (hasMore) {
            Icon(
                painter = painterResource(R.drawable.ic_right_arrow),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onClickHasMore() },
                tint = Color.Unspecified
            )
        }
    }
}
