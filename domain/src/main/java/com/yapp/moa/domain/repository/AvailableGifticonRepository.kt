package com.yapp.moa.domain.repository

import com.yapp.moa.domain.model.gifticon.AvailableGifticon
import com.yapp.moa.domain.model.type.GifticonStoreCategory
import com.yapp.moa.domain.model.type.SortType
import kotlinx.coroutines.flow.Flow

interface AvailableGifticonRepository {
    fun getAvailableGifiticon(
        gifticonStoreCategory: GifticonStoreCategory,
        gifticonSortType: SortType,
        currentPage: Int
    ): Flow<AvailableGifticon>
}
