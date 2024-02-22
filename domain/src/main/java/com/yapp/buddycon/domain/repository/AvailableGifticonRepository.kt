package com.yapp.buddycon.domain.repository

import com.yapp.buddycon.domain.model.gifticon.AvailableGifticon
import com.yapp.buddycon.domain.model.type.GifticonStoreCategory
import kotlinx.coroutines.flow.Flow

interface AvailableGifticonRepository {
    fun getAvailableGifiticon(gifticonStoreCategory: GifticonStoreCategory, currentPage: Int): Flow<AvailableGifticon> // todo - parameter 추가 예정
}
