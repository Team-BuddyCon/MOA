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
}
