package com.yapp.buddycon.domain.model.gifticon

import com.yapp.buddycon.domain.model.type.GifticonStore
import com.yapp.buddycon.domain.model.type.GifticonStoreCategory

data class UnavailableGifticon(
    val unAvailableGifticons: List<UnavailableGifticonInfo>,
    val isFirstPage: Boolean = false,
    val isLastPage: Boolean = false,
    val pageNumber: Int = 0,
) {
    data class UnavailableGifticonInfo(
        val gifticonId: Int = -1,
        val imageUrl: String = "",
        val category: GifticonStore = GifticonStore.OTHERS,
        val storeCategory: GifticonStoreCategory = GifticonStoreCategory.OTHERS,
        val name: String = "",
        val expireDate: String = "",
    )
}
