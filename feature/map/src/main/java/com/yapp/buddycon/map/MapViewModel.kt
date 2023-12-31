package com.yapp.buddycon.map

import androidx.lifecycle.ViewModel
import com.yapp.buddycon.domain.model.type.GifticonCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState = _uiState.asStateFlow()

    fun changeCategory(category: GifticonCategory) {
        _uiState.value = _uiState.value.copy(category = category)
    }

    fun changeSheetValue(sheetValue: BottomSheetValue) {
        _uiState.value = _uiState.value.copy(
            sheetValue = sheetValue,
            heightDp = when (sheetValue) {
                BottomSheetValue.Collapsed -> BottomSheetValue.Collapsed.sheetPeekHeightDp
                BottomSheetValue.PartiallyExpanded -> BottomSheetValue.PartiallyExpanded.sheetPeekHeightDp
                BottomSheetValue.Expanded -> BottomSheetValue.Expanded.sheetPeekHeightDp
                is BottomSheetValue.Moving -> uiState.value.heightDp
            }
        )
    }

    fun changeHeightDp(heightDp: Float) {
        _uiState.value = _uiState.value.copy(heightDp = heightDp)
    }
}
