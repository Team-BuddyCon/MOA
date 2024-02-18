package com.yapp.buddycon.gifticon

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GifticonViewModel @Inject constructor() : ViewModel() {

    private val _showErrorPopup = MutableStateFlow(false)
    val showErrorPopup = _showErrorPopup.asStateFlow()

    fun showGifticonRegisterError(isShow: Boolean) {
        _showErrorPopup.value = isShow
    }
}
