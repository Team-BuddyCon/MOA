package com.yapp.buddycon.designsystem.component.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.buddycon.designsystem.R
import com.yapp.buddycon.designsystem.theme.Aureolin
import com.yapp.buddycon.designsystem.theme.BuddyConTheme
import com.yapp.buddycon.designsystem.theme.Temptress

private val LoginButtonRadius = 12.dp
private val LoginButtonHeight = 54.dp
private val KakaoLoginIconDescription = "KakaoLoginIcon"

@Composable
fun LoginButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(LoginButtonHeight),
        shape = RoundedCornerShape(LoginButtonRadius),
        colors = ButtonDefaults.buttonColors(containerColor = Aureolin)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_kakao_logo),
            contentDescription = KakaoLoginIconDescription,
            modifier = Modifier.size(24.dp),
            tint = Color.Unspecified
        )
        Text(
            text = stringResource(R.string.login_kakao_talk),
            modifier = Modifier.padding(start = 12.dp),
            style = BuddyConTheme.typography.subTitle.copy(color = Temptress)
        )
    }
}

@Preview
@Composable
private fun LoginButtonPreview() {
    BuddyConTheme {
        LoginButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {}
        )
    }
}
