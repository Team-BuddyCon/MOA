package com.yapp.moa.domain.model.gifticon

import com.yapp.moa.domain.model.type.GifticonStore
import com.yapp.moa.domain.model.type.GifticonStoreCategory

data class GifticonDetailModel(
    val gifticonId: Int = Int.MIN_VALUE,
    val imageUrl: String = "",
    val name: String = "",
    val memo: String = "",
    val expireDate: String = "",
    val gifticonStore: GifticonStore = GifticonStore.OTHERS,
    val gifticonStoreCategory: GifticonStoreCategory = GifticonStoreCategory.OTHERS,
    val used: Boolean = false
)
