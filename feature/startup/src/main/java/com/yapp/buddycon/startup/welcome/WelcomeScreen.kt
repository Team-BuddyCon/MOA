package com.yapp.buddycon.startup.welcome

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.component.button.BuddyConButton
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Paddings
import com.yapp.buddycon.designsystem.theme.Pink100

@Composable
fun WelcomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BuddyConTheme.colors.background)
    ) {
        Spacer(Modifier.weight(152f))
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
            text = String.format(stringResource(R.string.welcome_greeting_format), "오원석"),
            modifier = Modifier
                .padding(horizontal = Paddings.xextra)
                .padding(top = Paddings.medium)
                .fillMaxWidth(),
            style = BuddyConTheme.typography.body01
        )
        Spacer(Modifier.weight(68f))
        Image(
            painter = painterResource(R.drawable.ic_welcome_illustration),
            contentDescription = null,
            modifier = Modifier.align(Alignment.End)
        )
        Spacer(Modifier.weight(75.24f))
        BuddyConButton(
            modifier = Modifier
                .padding(bottom = Paddings.xlarge)
                .padding(horizontal = Paddings.xlarge)
                .fillMaxWidth(),
            text = stringResource(R.string.start)
        ) {

        }
    }
}