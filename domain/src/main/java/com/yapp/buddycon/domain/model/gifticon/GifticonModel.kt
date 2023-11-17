package com.yapp.buddycon.domain.model.gifticon

import com.yapp.buddycon.domain.model.type.GifticonCategory

data class GifticonModel(
    val imageUrl: String = "",
    val category: GifticonCategory = GifticonCategory.ETC,
    val name: String = "",
    val expirationDate: String = ""
)
