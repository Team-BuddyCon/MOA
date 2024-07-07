package com.yapp.moa.startup.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.moa.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
) : ViewModel() {
    private val _userNickname = MutableStateFlow("")
    val userNickname = _userNickname.asStateFlow()

    init {
        getNickname()
    }

    private fun getNickname() {
        tokenRepository.getNickname()
            .onEach { _userNickname.value = it }
            .launchIn(viewModelScope)
    }
}
