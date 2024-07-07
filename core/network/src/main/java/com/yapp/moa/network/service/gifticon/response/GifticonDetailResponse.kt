package com.yapp.moa.network.service.gifticon.response

import com.yapp.moa.domain.model.gifticon.GifticonDetailModel
import com.yapp.moa.domain.model.type.GifticonStore
import com.yapp.moa.domain.model.type.GifticonStoreCategory

data class GifticonDetailResponse(
    val status: Int,
    val message: String,
    val body: GifticonDetailResponseBody
)

data class GifticonDetailResponseBody(
    val gifticonId: Int,
    val imageUrl: String,
    val name: String,
    val memo: String,
    val expireDate: String,
    val gifticonStore: String,
    val gifticonStoreCategory: String,
    val used: Boolean
) {
    fun toModel() = GifticonDetailModel(
        gifticonId = gifticonId,
        imageUrl = imageUrl,
        name = name,
        memo = memo,
        expireDate = expireDate,
        gifticonStore = GifticonStore.valueOf(gifticonStore),
        gifticonStoreCategory = GifticonStoreCategory.valueOf(gifticonStoreCategory),
        used = used
    )
}
