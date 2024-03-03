package com.yapp.buddycon.gifticon.register

import com.yapp.buddycon.domain.model.type.GifticonCategory

data class GifticonRegisterUiState(
    val name: String = "",
    val expireDate: Long = 0L,
    val category: GifticonCategory = GifticonCategory.ETC,
    val memo: String = ""
)
