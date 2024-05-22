package com.yapp.buddycon.startup.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.repository.AuthRepository
import com.yapp.buddycon.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val _effect = MutableSharedFlow<LoginSideEffect>()
    val effect = _effect.asSharedFlow()

    fun saveNickname(nickname: String) {
        viewModelScope.launch {
            tokenRepository.saveNickname(nickname)
        }
    }

    fun fetchLogin(
        oauthAccessToken: String,
        nickname: String,
        email: String?,
        gender: String?,
        age: String?
    ) {
        authRepository.fetchLogin(
            oauthAccessToken = oauthAccessToken,
            nickname = nickname,
            email = email ?: "",
            gender = gender ?: "",
            age = age ?: ""
        ).onEach {
            tokenRepository.saveAccessToken(it.accessToken)
            tokenRepository.saveRefreshToken(it.refreshToken)
            tokenRepository.saveAccessTokenExpiresIn(it.accessTokenExpiresIn)
            _effect.emit(if (it.isFirstLogin) LoginSideEffect.FirstLogin else LoginSideEffect.ReLogin)
        }.launchIn(viewModelScope)
    }

    fun handleKAKAOLoginError() {
        viewModelScope.launch {
            _effect.emit(LoginSideEffect.KakaoLoginError)
        }
    }

    fun handleMOALoginError() {
        viewModelScope.launch {
            _effect.emit(LoginSideEffect.MoaLoginError)
        }
    }
}
