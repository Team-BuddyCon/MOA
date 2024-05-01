package com.yapp.buddycon.domain.repository

import com.yapp.buddycon.domain.model.gifticon.GifticonDetailModel
import com.yapp.buddycon.domain.model.gifticon.GifticonModel
import kotlinx.coroutines.flow.Flow

interface GifticonRepository {
    fun createGifticon(
        imagePath: String,
        name: String,
        expireDate: String,
        store: String,
        memo: String
    ): Flow<Int>

    fun requestGifticonDetail(
        gifticonId: Int
    ): Flow<GifticonDetailModel>

    fun editGifticonDetail(
        gifticonId: Int,
        name: String,
        expireDate: String,
        gifticonStore: String,
        memo: String
    ): Flow<Unit>
}
