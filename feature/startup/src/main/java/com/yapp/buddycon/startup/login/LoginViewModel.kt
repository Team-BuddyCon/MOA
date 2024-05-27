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
import java.util.Calendar
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

    fun fetchTestLogin() {
        val accessToken =
            "eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MSwiaWF0IjoxNzE2NzA3ODM1LCJleHAiOjE3MjI3NTU4MzV9.Afm0jsuHLVwg0YbiR1unky1xZkrd0B-yDJhAtAwixgZM5j1dv4kMHhjw279P1yOOFndkfJ_SVD30uQ9kZR69wA" // ktlint-disable max-line-length
        val accessTokenExpiresIn = Calendar.getInstance().apply {
            add(Calendar.YEAR, 100)
        }.timeInMillis

        viewModelScope.launch {
            tokenRepository.saveNickname("TEST")
            tokenRepository.saveAccessToken(accessToken)
            tokenRepository.saveAccessTokenExpiresIn(accessTokenExpiresIn)
            _effect.emit(LoginSideEffect.ReLogin)
        }
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
