package com.yapp.buddycon.map

import com.yapp.buddycon.domain.model.type.GifticonCategory

data class MapUiState(
    val category: GifticonCategory = GifticonCategory.TOTAL,
    val sheetValue: BottomSheetValue = BottomSheetValue.PartiallyExpanded,
    val heightDp: Float = 36f
)

sealed class BottomSheetValue(open val sheetPeekHeightDp: Float) {

    object Collapsed : BottomSheetValue(36f)

    object PartiallyExpanded : BottomSheetValue(111f)

    object Expanded : BottomSheetValue(540f)

    data class Moving(
        override val sheetPeekHeightDp: Float
    ) : BottomSheetValue(sheetPeekHeightDp)
}
