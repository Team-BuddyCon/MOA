package com.yapp.buddycon.domain.model.gifticon

import com.yapp.buddycon.domain.model.type.GifticonStore

data class GifticonModel(
    val imageUrl: String = "",
    val category: GifticonStore = GifticonStore.OTHERS,
    val name: String = "",
    val expirationTime: Long = 0L
) 
