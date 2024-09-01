package com.yapp.moa.gifticon.register

import com.yapp.moa.domain.model.type.GifticonStore

data class GifticonRegisterUiState(
    val name: String = "",
    val expireDate: Long = 0L,
    val category: GifticonStore = GifticonStore.NONE,
    val memo: String = ""
)
