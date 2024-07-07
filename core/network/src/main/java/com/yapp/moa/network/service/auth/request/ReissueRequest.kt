package com.yapp.moa.network.service.auth.request

data class ReissueRequest(
    val accessToken: String,
    val refreshToken: String
)
