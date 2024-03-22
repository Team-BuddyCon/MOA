package com.yapp.buddycon.domain.model.gifticon

import com.yapp.buddycon.domain.model.type.GifticonCategory
import com.yapp.buddycon.domain.model.type.GifticonStoreCategory

data class GifticonDetailModel(
    val gifticonId: Int = Int.MIN_VALUE,
    val imageUrl: String = "",
    val name: String = "",
    val memo: String = "",
    val expireDate: String = "",
    val gifticonStore: GifticonCategory = GifticonCategory.ETC,
    val gifticonStoreCategory: GifticonStoreCategory = GifticonStoreCategory.OTHERS
)
