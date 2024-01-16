package com.yapp.buddycon.domain.repository

import com.yapp.buddycon.domain.model.gifticon.AvailableGifticon
import kotlinx.coroutines.flow.Flow

interface AvailableGifticonRepository {
    fun getAvailableGifiticon(): Flow<AvailableGifticon> // todo - parameter 추가 예정
}
