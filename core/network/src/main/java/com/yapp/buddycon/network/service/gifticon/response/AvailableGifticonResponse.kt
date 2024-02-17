package com.yapp.buddycon.network.service.gifticon.response

import com.google.gson.annotations.SerializedName
import com.yapp.buddycon.domain.model.gifticon.AvailableGifticon

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
        val expireDate: String,
        val gifticonId: Int,
        val gifticonStore: String,
        val gifticonStoreCategory: String,
        val imageUrl: String,
        val memo: String,
        val name: String
    ) {
        fun mapToAvailableGifticonInfo() = AvailableGifticon.AvailableGifticonInfo(
            imageUrl = this.imageUrl,
            name = this.name,
            expireDate = this.expireDate
            // 가게, 메뉴 카테고리 정보 mapping 추가 예정
        )
    }

    data class Pageable(
        val pageNumber: Int,
        val pageSize: Int,
        val paged: Boolean,
        val unpaged: Boolean
    )

    fun mapToAvailableGifticon() = AvailableGifticon(
        availableGifticons = this.content.map { it.mapToAvailableGifticonInfo() },
        isFirstPage = this.first,
        isLastPage = this.last,
        pageNumber = this.pageable.pageNumber
    )
}
