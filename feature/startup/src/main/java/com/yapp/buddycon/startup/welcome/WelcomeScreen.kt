package com.yapp.buddycon.startup.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.component.button.BuddyConButton
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Paddings
import com.yapp.buddycon.designsystem.theme.Pink100

@Composable
fun WelcomeScreen(
    welcomeViewModel: WelcomeViewModel = hiltViewModel(),
    onNavigateToGifticon: () -> Unit = {}
) {
    val userNickname by welcomeViewModel.userNickname.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BuddyConTheme.colors.background)
    ) {
        Spacer(Modifier.weight(242f))
        Icon(
            painter = painterResource(R.drawable.ic_welcome),
            contentDescription = null,
            modifier = Modifier
                .padding(start = Paddings.xextra)
                .size(50.dp),
            tint = Color.Unspecified
        )
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(Pink100)) {
                    append(stringResource(R.string.signup))
                }
                append(" ${stringResource(R.string.complete)}")
            },
            modifier = Modifier
                .padding(horizontal = Paddings.xextra)
                .padding(top = Paddings.medium)
                .fillMaxWidth(),
            style = BuddyConTheme.typography.largeTitle
        )
        Text(
            text = String.format(stringResource(R.string.welcome_greeting_format), userNickname),
            modifier = Modifier
                .padding(horizontal = Paddings.xextra)
                .padding(top = Paddings.medium)
                .fillMaxWidth(),
            style = BuddyConTheme.typography.body01
        )
        Spacer(Modifier.weight(260f))
        BuddyConButton(
            modifier = Modifier
                .padding(bottom = Paddings.xlarge)
                .padding(horizontal = Paddings.xlarge)
                .fillMaxWidth(),
            text = stringResource(R.string.start),
            onClick = onNavigateToGifticon
        )
    }
}
