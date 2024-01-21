package com.yapp.buddycon.map

import com.yapp.buddycon.domain.model.type.GifticonCategory

data class MapUiState(
    val category: GifticonCategory = GifticonCategory.TOTAL,
    val sheetValue: BottomSheetValue = BottomSheetValue.Collapsed,
    val heightDp: Float = BottomSheetValue.Collapsed.sheetPeekHeightDp
)

enum class BottomSheetValue(val sheetPeekHeightDp: Float) {
    Collapsed(36f),
    PartiallyExpanded(111f),
    Expanded(540f)
}
