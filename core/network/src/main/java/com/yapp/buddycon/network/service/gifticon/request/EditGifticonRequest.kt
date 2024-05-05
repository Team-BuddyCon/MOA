package com.yapp.buddycon.network.service.gifticon.request

data class EditGifticonRequest(
    val name: String,
    val expireDate: String,
    val store: String,
    val memo: String
)
