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

@HiltViewModel
class MapViewModel @Inject constructor(
    private val gifticonRepository: GifticonRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState = _uiState.asStateFlow()

    private val _gifticonItems = MutableStateFlow<PagingData<AvailableGifticon.AvailableGifticonInfo>>(PagingData.empty())
    val gifticonItems = _gifticonItems.asStateFlow()

    private val _gifticonStore = MutableStateFlow(GifticonStore.TOTAL)
    val gifticonStore = _gifticonStore.asStateFlow()

    init {
        fetchAvailableGifticon()
    }

    private fun fetchAvailableGifticon() {
        gifticonRepository.fetchAvailableGifticon(
            gifticonStore = gifticonStore.value,
            gifticonSortType = SortType.EXPIRATION_DATE
        ).cachedIn(viewModelScope)
            .onEach {
                _gifticonItems.value = it
            }.launchIn(viewModelScope)
    }

    fun onCategoryChange(category: GifticonStore) {
        _uiState.value = _uiState.value.copy(category = category)
    }

    fun onSheetValueChange(sheetValue: BottomSheetValue) {
        _uiState.value = _uiState.value.copy(
            sheetValue = sheetValue,
            heightDp = when (sheetValue) {
                BottomSheetValue.Collapsed -> BottomSheetValue.Collapsed.sheetPeekHeightDp
                BottomSheetValue.PartiallyExpanded -> BottomSheetValue.PartiallyExpanded.sheetPeekHeightDp
                BottomSheetValue.Expanded -> BottomSheetValue.Expanded.sheetPeekHeightDp
            },
            offset = 0f
        )
    }

    fun changeBottomSheetOffset(offsetY: Float) {
        _uiState.value = _uiState.value.copy(
            heightDp = _uiState.value.heightDp - offsetY,
            offset = _uiState.value.offset - offsetY
        )
    }
}
