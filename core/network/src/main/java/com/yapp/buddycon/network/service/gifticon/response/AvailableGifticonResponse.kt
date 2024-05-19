package com.yapp.buddycon.network.service.gifticon.response

import android.util.Log
import com.google.gson.annotations.SerializedName
import com.yapp.buddycon.domain.model.gifticon.AvailableGifticon
import com.yapp.buddycon.domain.model.gifticon.UnavailableGifticon
import com.yapp.buddycon.domain.model.type.mapStringValueToGifticonStore
import com.yapp.buddycon.domain.model.type.mapStringValueToGifiticonStoreCategory

data class AvailableGifticonResponse(
    @SerializedName("body")
    val body: Body,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)

data class Body(
    val content: List<Content>,
    val empty: Boolean,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val pageable: Pageable,
    val size: Int
) {
    data class Content(
        val gifticonId: Int,
        val imageUrl: String,
        val name: String,
        val memo: String,
        val expireDate: String,
        val gifticonStore: String,
        val gifticonStoreCategory: String,
    ) {
        fun mapToAvailableGifticonInfo() = AvailableGifticon.AvailableGifticonInfo(
            gifticonId = gifticonId,
            imageUrl = this.imageUrl,
            name = this.name,
            expireDate = this.expireDate,
            category = mapStringValueToGifticonStore(gifticonStore),
            storeCategory = mapStringValueToGifiticonStoreCategory(gifticonStoreCategory)
        )

        fun mapToUnavailableGifticonInfo() = UnavailableGifticon.UnavailableGifticonInfo(
            gifticonId = gifticonId,
            imageUrl = this.imageUrl,
            name = this.name,
            expireDate = this.expireDate,
            category = mapStringValueToGifticonStore(gifticonStore),
            storeCategory = mapStringValueToGifiticonStoreCategory(gifticonStoreCategory)
        )
    }

    data class Pageable(
        val pageNumber: Int,
        val pageSize: Int,
        val paged: Boolean,
        val unpaged: Boolean
    )

    fun mapToAvailableGifticon() = AvailableGifticon(
        availableGifticons = this.content.map { it.mapToAvailableGifticonInfo() }.also { Log.e("MOATest", "size : ${this.content.size}") },
        isFirstPage = this.first,
        isLastPage = this.last,
        pageNumber = this.pageable.pageNumber
    )

    fun mapToUnavailableGifticon() = UnavailableGifticon(
        unAvailableGifticons = this.content.map { it.mapToUnavailableGifticonInfo() }.also { Log.e("MOATest", "size : ${this.content.size}") },
        isFirstPage = this.first,
        isLastPage = this.last,
        pageNumber = this.pageable.pageNumber
    )
}
