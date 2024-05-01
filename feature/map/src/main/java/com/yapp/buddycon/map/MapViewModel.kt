package com.yapp.buddycon.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yapp.buddycon.domain.model.gifticon.AvailableGifticon
import com.yapp.buddycon.domain.model.type.GifticonStore
import com.yapp.buddycon.domain.model.type.SortType
import com.yapp.buddycon.domain.repository.GifticonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

enum class BottomSheetValue(val sheetPeekHeightDp: Float) {
    Collapsed(36f),
    PartiallyExpanded(111f),
    Expanded(540f)
}

@HiltViewModel
class MapViewModel @Inject constructor(
    private val gifticonRepository: GifticonRepository
) : ViewModel() {

    private var _store = MutableStateFlow(GifticonStore.TOTAL)
    val store = _store.asStateFlow()

    private var _sheetValue = MutableStateFlow(BottomSheetValue.Collapsed)
    val sheetValue = _sheetValue.asStateFlow()

    private var _heightDp = MutableStateFlow(BottomSheetValue.Collapsed.sheetPeekHeightDp)
    val heightDp = _heightDp.asStateFlow()

    private var _offset = MutableStateFlow(0f)
    val offset = _offset.asStateFlow()

    private val _gifticonPagingItems = MutableStateFlow<PagingData<AvailableGifticon.AvailableGifticonInfo>>(PagingData.empty())
    val gifticonPagingItems = _gifticonPagingItems.asStateFlow()

    private val _totalCount = MutableStateFlow(0)
    val totalCount = _totalCount.asStateFlow()

    init {
        fetchAvailableGifticon()
        getGifticonCount()
    }

    private fun fetchAvailableGifticon() {
        gifticonRepository.fetchAvailableGifticon(
            gifticonStore = store.value,
            gifticonSortType = SortType.EXPIRATION_DATE
        ).cachedIn(viewModelScope)
            .onEach {
                _gifticonPagingItems.value = it
            }.launchIn(viewModelScope)
    }

    private fun getGifticonCount() {
        gifticonRepository.getGifticonCount(used = false)
            .onEach {
                _totalCount.value = it
            }.launchIn(viewModelScope)
    }

    fun selectGifticonStore(store: GifticonStore) {
        _store.value = store
    }

    fun changeBottomSheetValue(sheetValue: BottomSheetValue) {
        _sheetValue.value = sheetValue
        _heightDp.value = sheetValue.sheetPeekHeightDp
        _offset.value = 0f
    }

    fun setOffset(offset: Float) {
        _heightDp.value -= offset
        _offset.value = -offset
    }
}
