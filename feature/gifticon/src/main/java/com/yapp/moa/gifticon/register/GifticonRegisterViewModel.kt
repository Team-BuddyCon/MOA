package com.yapp.moa.gifticon.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.moa.domain.model.type.GifticonStore
import com.yapp.moa.domain.repository.GifticonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class GifticonRegisterViewModel @Inject constructor(
    private val gifticonRepository: GifticonRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GifticonRegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val _gifticonId = MutableSharedFlow<Int>()
    val gifticonId = _gifticonId.asSharedFlow()

    fun setName(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun setExpirationDate(expireDate: Long) {
        _uiState.value = _uiState.value.copy(expireDate = expireDate)
    }

    fun setCategory(category: GifticonStore) {
        _uiState.value = _uiState.value.copy(category = category)
    }

    fun setMemo(memo: String) {
        _uiState.value = _uiState.value.copy(memo = memo)
    }

    fun registerNewGifticon(imagePath: String) {
        gifticonRepository.createGifticon(
            imagePath = imagePath,
            name = uiState.value.name,
            expireDate = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(Date(uiState.value.expireDate)),
            store = uiState.value.category.name,
            memo = uiState.value.memo
        ).onEach {
            _gifticonId.emit(it)
        }.launchIn(viewModelScope)
    }
}
