package com.yapp.moa.network.service.gifticon.response

data class GifticonBasicResponse(
    val statue: Int,
    val message: String,
    val body: Int
) {
    fun mapToUnit() = Unit
}
