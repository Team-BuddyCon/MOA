package com.yapp.buddycon.map

import androidx.lifecycle.ViewModel
import com.yapp.buddycon.domain.model.type.GifticonStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState = _uiState.asStateFlow()
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
            }
        )
    }

    fun changeBottomSheetOffset(offsetY: Float) {
        _uiState.value = _uiState.value.copy(
            heightDp = _uiState.value.heightDp - offsetY
        )
    }
}
