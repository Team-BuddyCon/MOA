package com.yapp.buddycon.network.service.gifticon.response

data class GifticonCountResponse(
    val status: Int,
    val message: String,
    val body: GifticonCountResponseBody
)

data class GifticonCountResponseBody(
    val count: Int
) 
