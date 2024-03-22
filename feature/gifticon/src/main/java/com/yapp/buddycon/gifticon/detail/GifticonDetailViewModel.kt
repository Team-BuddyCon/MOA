package com.yapp.buddycon.gifticon.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.model.gifticon.GifticonDetailModel
import com.yapp.buddycon.domain.repository.GifticonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GifticonDetailViewModel @Inject constructor(
    private val gifticonRepository: GifticonRepository
) : ViewModel() {
    private val _gifticonDetailModel = MutableStateFlow<GifticonDetailModel>(GifticonDetailModel())
    val gifticonDetailModel = _gifticonDetailModel.asStateFlow()

    fun requestGifticonDetail(gifticonId: Int) {
        gifticonRepository.requestGifticonDetail(gifticonId)
            .onEach { _gifticonDetailModel.value = it }
            .launchIn(viewModelScope)
    }
}
