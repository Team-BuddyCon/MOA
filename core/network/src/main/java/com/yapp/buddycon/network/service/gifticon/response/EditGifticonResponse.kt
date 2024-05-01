package com.yapp.buddycon.network.service.gifticon.response

data class EditGifticonResponse(
    val statue: Int,
    val message: String,
    val body: Int
) {
    fun mapToUnit() = Unit
}
