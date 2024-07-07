package com.yapp.moa.startup.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.moa.domain.repository.AuthRepository
import com.yapp.moa.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _isTestMode = MutableStateFlow(false)
    val isTestMode = _isTestMode.asStateFlow()

    init {
        getTestModeValue()
    }

    private fun getTestModeValue() {
        tokenRepository.isTestMode()
            .onEach { _isTestMode.value = it }
            .launchIn(viewModelScope)
    }

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
            "eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MSwiaWF0IjoxNzE3ODQ1MDAzLCJleHAiOjE3MjM4OTMwMDN9.wNxFHkYU7vyFIh5ErZem18_WUSDV8hdlINzcqOZdrzrplQpAaMj8ZDax6OpWzqmrftPTCV4z2sjT7Rz6SEFdRw" // ktlint-disable max-line-length
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
