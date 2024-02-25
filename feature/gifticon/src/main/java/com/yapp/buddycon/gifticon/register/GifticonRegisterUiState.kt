package com.yapp.buddycon.gifticon.register

data class GifticonRegisterUiState(
    val name: String = "",
    val expireDate: Long = 0L,
    val store: String = "",
    val memo: String = ""
)
