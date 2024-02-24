package com.yapp.buddycon.network.service.gifticon.request

data class CreateGifticonRequest(
    val name: String,
    val expireDate: String,
    val store: String,
    val memo: String
)
