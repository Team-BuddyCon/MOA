package com.yapp.moa.startup.login

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.yapp.moa.designsystem.R
import com.yapp.moa.designsystem.component.button.BuddyConButton
import com.yapp.moa.designsystem.component.button.LoginButton
import com.yapp.moa.designsystem.component.dialog.ConfirmDialog
import com.yapp.moa.designsystem.theme.BuddyConTheme
import com.yapp.moa.designsystem.theme.Paddings
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Calendar
import kotlin.coroutines.resume

private val LoginBannerDescription = "LoginBanner"
private val BuddyConLogoDescription = "BuddyConLogo"

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    onNavigateToSignUp: () -> Unit = {},
    onNavigateToGifticon: () -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isShowKakaoLoginError by remember { mutableStateOf(false) }
    var isShowMoaLoginError by remember { mutableStateOf(false) }
    val isTestMode by loginViewModel.isTestMode.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        loginViewModel.effect.collect { effect ->
            when (effect) {
                LoginSideEffect.FirstLogin -> {
                    onNavigateToSignUp()
                }

                LoginSideEffect.ReLogin -> {
                    onNavigateToGifticon()
                }

                LoginSideEffect.KakaoLoginError -> {
                    isShowKakaoLoginError = true
                }

                LoginSideEffect.MoaLoginError -> {
                    isShowMoaLoginError = true
                }
            }
        }
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(BuddyConTheme.colors.background)

    if (isShowKakaoLoginError) {
        ConfirmDialog(
            dialogTitle = stringResource(R.string.kakao_login_error_title),
            dialogContent = stringResource(R.string.kakao_login_error_description),
            onClick = { isShowKakaoLoginError = false },
            onDismissRequest = { isShowKakaoLoginError = false }
        )
    }

    if (isShowMoaLoginError) {
        ConfirmDialog(
            dialogTitle = stringResource(R.string.moa_login_error_title),
            dialogContent = stringResource(R.string.moa_login_error_description),
            onClick = { isShowMoaLoginError = false },
            onDismissRequest = { isShowMoaLoginError = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BuddyConTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(279f))
        Image(
            painter = painterResource(R.drawable.ic_full_logo),
            contentDescription = LoginBannerDescription,
            modifier = Modifier.size(97.dp),
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = stringResource(R.string.login_banner_description),
            style = BuddyConTheme.typography.body03,
            textAlign = TextAlign.Center
        )
        if (isTestMode) {
            Spacer(Modifier.weight(184f))
            BuddyConButton(
                modifier = Modifier
                    .padding(bottom = Paddings.medium)
                    .padding(horizontal = Paddings.xlarge)
                    .fillMaxWidth(),
                text = stringResource(R.string.login_test_mode),
                containerColor = BuddyConTheme.colors.primary,
                contentColor = BuddyConTheme.colors.onPrimary
            ) {
                loginViewModel.fetchTestLogin()
            }
        } else {
            Spacer(Modifier.weight(238f))
        }
        LoginButton(
            modifier = Modifier
                .padding(bottom = Paddings.xlarge)
                .padding(horizontal = Paddings.xlarge)
                .fillMaxWidth()
        ) {
            coroutineScope.launch {
                val oauthToken = loginKakao(context, loginViewModel)
                oauthToken?.let { token ->
                    UserApiClient.instance.me { user, error ->
                        error?.let {
                            loginViewModel.handleMOALoginError()
                            return@me
                        }

                        user?.let {
                            it.kakaoAccount?.profile?.nickname?.let { nickname ->
                                loginViewModel.saveNickname(nickname)
                                loginViewModel.fetchLogin(
                                    oauthAccessToken = token.accessToken,
                                    nickname = nickname,
                                    age = it.kakaoAccount?.birthyear?.let {
                                        (Calendar.getInstance().get(Calendar.YEAR) - it.toInt()).toString()
                                    },
                                    email = it.kakaoAccount?.email,
                                    gender = it.kakaoAccount?.gender?.name
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private suspend fun loginKakao(
    context: Context,
    loginViewModel: LoginViewModel
): OAuthToken? = suspendCancellableCoroutine { continuation ->
    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            error?.let { e ->
                if (e is ClientError && e.reason == ClientErrorCause.Cancelled) return@loginWithKakaoTalk
                loginWithKakaoAccount(context, loginViewModel, continuation)
            } ?: kotlin.run {
                continuation.resume(token)
            }
        }
    } else {
        loginWithKakaoAccount(context, loginViewModel, continuation)
    }
}

private fun loginWithKakaoAccount(
    context: Context,
    loginViewModel: LoginViewModel,
    continuation: CancellableContinuation<OAuthToken?>
) {
    UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
        error?.let {
            loginViewModel.handleKAKAOLoginError()
            continuation.resume(null)
        } ?: kotlin.run {
            continuation.resume(token)
        }
    }
}
