package com.yapp.moa.map

import com.yapp.moa.domain.model.type.GifticonStore
import com.yapp.moa.utility.stability.MapSearchPlace

data class MapUiState(
    val sheetValue: BottomSheetValue = BottomSheetValue.Collapsed,
    val heightDp: Float = BottomSheetValue.Collapsed.sheetPeekHeightDp,
    val offset: Float = 0f,
    val store: GifticonStore = GifticonStore.TOTAL,
    val totalCount: Int = 0,
    val deadLineCount: Int = 0,
    val mapSearchPlace: MapSearchPlace = MapSearchPlace(),
)
