package com.yapp.buddycon.startup.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val _isFirstInstallation = MutableStateFlow(false)
    val isFirstInstallation = _isFirstInstallation.asStateFlow()

    init {
        getIsFirstInstallation()
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
}
