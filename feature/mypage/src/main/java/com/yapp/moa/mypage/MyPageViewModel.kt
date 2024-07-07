package com.yapp.moa.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.moa.domain.repository.GifticonRepository
import com.yapp.moa.domain.repository.MemberRepository
import com.yapp.moa.domain.repository.NotificationRepository
import com.yapp.moa.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val memberRepository: MemberRepository,
    private val tokenRepository: TokenRepository,
    private val gifticonRepository: GifticonRepository,
    private val notificationRepository: NotificationRepository
) : ViewModel() {
    private var _userName = MutableStateFlow("")
    val userName = _userName.asStateFlow()

    private var _usedGifticonCount = MutableStateFlow(0)
    val usedGifticonCount = _usedGifticonCount.asStateFlow()

    private val _logoutEvent = MutableStateFlow(false)
    val logoutEvent = _logoutEvent.asStateFlow()

    private val _isTestMode = MutableStateFlow(false)
    val isTestMode = _isTestMode.asStateFlow()

    private val _isActiveNotification = MutableStateFlow(false)
    val isActiveNotification = _isActiveNotification.asStateFlow()

    init {
        loadUserName()
        getUsedGifticonCount()
        getTestModel()
    }

    fun getNotificationSettings() {
        notificationRepository.getNotificationSettings()
            .onEach { _isActiveNotification.value = it.activated }
            .launchIn(viewModelScope)
    }

    private fun loadUserName() {
        tokenRepository.getNickname()
            .onEach { _userName.value = it }
            .launchIn(viewModelScope)
    }

    private fun getTestModel() {
        tokenRepository.isTestMode()
            .onEach { _isTestMode.value = it }
            .launchIn(viewModelScope)
    }

    fun getUsedGifticonCount() {
        gifticonRepository.getGifticonCount(used = true)
            .onEach {
                _usedGifticonCount.value = it
            }.launchIn(viewModelScope)
    }

    fun fetchLogout() {
        memberRepository.fetchLogout()
            .onEach { _logoutEvent.emit(it) }
            .onCompletion {
                tokenRepository.saveAccessToken("")
                tokenRepository.saveRefreshToken("")
                tokenRepository.saveAccessTokenExpiresIn(Long.MIN_VALUE)
            }.launchIn(viewModelScope)
    }
}
