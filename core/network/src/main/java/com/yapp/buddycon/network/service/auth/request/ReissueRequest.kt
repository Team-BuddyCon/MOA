package com.yapp.buddycon.network.service.auth.request

data class ReissueRequest(
    val accessToken: String,
    val refreshToken: String
)
