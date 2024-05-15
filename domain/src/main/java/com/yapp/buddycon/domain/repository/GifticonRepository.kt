package com.yapp.buddycon.domain.repository

import androidx.paging.PagingData
import com.yapp.buddycon.domain.model.gifticon.AvailableGifticon
import com.yapp.buddycon.domain.model.gifticon.GifticonDetailModel
import com.yapp.buddycon.domain.model.type.GifticonStore
import com.yapp.buddycon.domain.model.type.GifticonStoreCategory
import com.yapp.buddycon.domain.model.type.SortType
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

    fun fetchAvailableGifticon(
        gifticonStoreCategory: GifticonStoreCategory? = null,
        gifticonStore: GifticonStore? = null,
        gifticonSortType: SortType? = null,
    ): Flow<PagingData<AvailableGifticon.AvailableGifticonInfo>>

    fun getGifticonCount(
        used: Boolean
    ): Flow<Int>

    fun editGifticonDetail(
        gifticonId: Int,
        name: String,
        expireDate: String,
        gifticonStore: String,
        memo: String
    ): Flow<Unit>

    fun updateGifticonUsedState(
        gifticonId: Int,
        used: Boolean
    ): Flow<Unit>
}
