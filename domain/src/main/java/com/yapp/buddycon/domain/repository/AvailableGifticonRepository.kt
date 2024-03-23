package com.yapp.buddycon.domain.repository

import com.yapp.buddycon.domain.model.gifticon.AvailableGifticon
import com.yapp.buddycon.domain.model.type.GifticonStoreCategory
import com.yapp.buddycon.domain.model.type.SortType
import kotlinx.coroutines.flow.Flow

interface AvailableGifticonRepository {
    fun getAvailableGifiticon(
        gifticonStoreCategory: GifticonStoreCategory,
        gifticonSortType: SortType,
        currentPage: Int
    ): Flow<AvailableGifticon>
}
