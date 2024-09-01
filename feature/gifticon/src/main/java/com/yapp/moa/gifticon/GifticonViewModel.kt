package com.yapp.moa.gifticon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.moa.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GifticonViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val _showErrorPopup = MutableStateFlow(false)
    val showErrorPopup = _showErrorPopup.asStateFlow()

    private val _showCoachMark = MutableStateFlow(false)
    val showCoachMark = _showCoachMark.asStateFlow()

    private val _firstExternalStorage = MutableStateFlow(false)
    val firstExternalStorage = _firstExternalStorage.asStateFlow()

    init {
        confirmCoachMark()
        confirmFirstExternalStorage()
    }

    fun showGifticonRegisterError(isShow: Boolean) {
        _showErrorPopup.value = isShow
    }

    private fun confirmCoachMark() {
        tokenRepository.isShouldShowCoachMark()
            .onEach {
                _showCoachMark.value = it
            }.launchIn(viewModelScope)
    }

    fun stopCoachMark() {
        viewModelScope.launch {
            tokenRepository.stopShowCoachMark()
        }
    }

    private fun confirmFirstExternalStorage() {
        tokenRepository.isFirstExternalStoragePopup()
            .onEach {
                _firstExternalStorage.value = it
            }.launchIn(viewModelScope)
    }

    fun checkFirstExternalStorage() {
        viewModelScope.launch {
            tokenRepository.checkFirstExternalStoragePopup()
        }
    }
}
