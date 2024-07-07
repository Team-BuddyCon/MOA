package com.yapp.moa.network.service.gifticon.response

import com.google.gson.annotations.SerializedName

data class UnavailableGifticonResponse(
    @SerializedName("body")
    val body: Body,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)
