package com.yapp.moa.mypage.usedgifticon

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yapp.moa.domain.model.gifticon.UnavailableGifticon
import com.yapp.moa.domain.repository.GifticonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UnavailableGifticonViewModel @Inject constructor(
    private val gifticonRepository: GifticonRepository
) : ViewModel() {
    private val _uanAvailableGifticonPagingItems = MutableStateFlow<PagingData<UnavailableGifticon.UnavailableGifticonInfo>>(PagingData.empty())
    val unAvailableGifticonPagingItems = _uanAvailableGifticonPagingItems.asStateFlow()

    private var _unAvailableGifticonCount = MutableStateFlow(0)
    val unAvailableGifticonCount = _unAvailableGifticonCount.asStateFlow()

    init {
        getUnavailableGifticonCount()
        getUnavailableGifticon()
        Log.d("MOATest", "UnavailableGifticonViewModel init")
    }

    private fun getUnavailableGifticon() {
        gifticonRepository.fetchUnavailableGifticon().cachedIn(viewModelScope)
            .onEach {
                _uanAvailableGifticonPagingItems.value = it
            }.launchIn(viewModelScope)
    }

    private fun getUnavailableGifticonCount() {
        gifticonRepository.getGifticonCount(used = true)
            .onEach {
                _unAvailableGifticonCount.value = it
            }.launchIn(viewModelScope)
    }
}
