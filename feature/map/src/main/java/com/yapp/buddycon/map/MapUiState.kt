package com.yapp.buddycon.map

import com.yapp.buddycon.domain.model.type.GifticonStore

data class MapUiState(
    val category: GifticonStore = GifticonStore.TOTAL,
    val sheetValue: BottomSheetValue = BottomSheetValue.Collapsed,
    val heightDp: Float = BottomSheetValue.Collapsed.sheetPeekHeightDp,
    val offset: Float = 0f
)

enum class BottomSheetValue(val sheetPeekHeightDp: Float) {
    Collapsed(36f),
    PartiallyExpanded(111f),
    Expanded(540f)
}
