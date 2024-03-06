package com.yapp.buddycon.startup.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.model.auth.LoginModel
import com.yapp.buddycon.domain.repository.AuthRepository
import com.yapp.buddycon.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val _isFirstInstallation = MutableStateFlow(true)
    val isFirstInstallation = _isFirstInstallation.asStateFlow()

    private val _loginToken = MutableStateFlow(LoginModel())
    val loginToken = _loginToken.asStateFlow()

    init {
        getIsFirstInstallation()
        getLoginToken()
    }

    private fun getIsFirstInstallation() {
        tokenRepository.isFirstInstallation()
            .onEach { _isFirstInstallation.value = it }
            .launchIn(viewModelScope)
    }

    fun checkIsFirstInstallation() {
        viewModelScope.launch {
            tokenRepository.checkFirstInstallation()
        }
    }

    private fun getLoginToken() {
        combine(
            tokenRepository.getAccessToken(),
            tokenRepository.getRefreshToken(),
            tokenRepository.getAccessTokenExpiresIn()
        ) { accessToken, refreshToken, accessTokenExpiresIn ->
            Timber.d("getLoginToken accessToken: $accessToken, currentTime : ${System.currentTimeMillis()}, accessTokenExpiresIn; $accessTokenExpiresIn")
            LoginModel(
                accessToken = accessToken,
                refreshToken = refreshToken,
                accessTokenExpiresIn = accessTokenExpiresIn
            )
        }.onEach { _loginToken.value = it }
            .launchIn(viewModelScope)
    }

    fun fetchReissue() {
        authRepository.fetchReissue(
            accessToken = _loginToken.value.accessToken,
            refreshToken = _loginToken.value.refreshToken
        ).onEach {
            _loginToken.value = it
            tokenRepository.saveAccessToken(it.accessToken)
            tokenRepository.saveRefreshToken(it.refreshToken)
            tokenRepository.saveAccessTokenExpiresIn(it.accessTokenExpiresIn)
        }.launchIn(viewModelScope)
    }
}
