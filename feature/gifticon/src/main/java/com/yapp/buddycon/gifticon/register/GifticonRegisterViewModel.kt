package com.yapp.buddycon.gifticon.register

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class GifticonRegisterViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(GifticonRegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun setName(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun setExpirationDate(long: Long) {
        val DATE_FORMAT = "yyyy년 MM월 dd일"
        _uiState.value = _uiState.value.copy(
            expiration_date = SimpleDateFormat(DATE_FORMAT, Locale.KOREA).format(long)
        )
    }

    fun setUsage(usage: String) {
        _uiState.value = _uiState.value.copy(usage = usage)
    }

    fun setMemo(memo: String) {
        _uiState.value = _uiState.value.copy(memo = memo)
    }
}
