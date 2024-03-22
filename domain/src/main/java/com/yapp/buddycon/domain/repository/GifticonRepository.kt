package com.yapp.buddycon.domain.repository

import com.yapp.buddycon.domain.model.gifticon.GifticonDetailModel
import kotlinx.coroutines.flow.Flow

interface GifticonRepository {
    fun createGifticon(
        imagePath: String,
        name: String,
        expireDate: String,
        store: String,
        memo: String
    ): Flow<Boolean>

    fun requestGifticonDetail(
        gifticonId: Int
    ): Flow<GifticonDetailModel>
}
