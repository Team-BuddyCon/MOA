package com.yapp.buddycon.network.service.gifticon.response

import com.yapp.buddycon.domain.model.gifticon.GifticonDetailModel
import com.yapp.buddycon.domain.model.type.GifticonCategory
import com.yapp.buddycon.domain.model.type.GifticonStoreCategory

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
    val gifticonStoreCategory: String
) {
    fun toModel() = GifticonDetailModel(
        gifticonId = gifticonId,
        imageUrl = imageUrl,
        name = name,
        memo = memo,
        expireDate = expireDate,
        gifticonStore = GifticonCategory.valueOf(gifticonStore),
        gifticonStoreCategory = GifticonStoreCategory.valueOf(gifticonStoreCategory)
    )
}
