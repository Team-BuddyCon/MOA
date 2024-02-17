package com.yapp.buddycon.network.service.auth.request

data class LoginRequest(
    val oauthAccessToken: String,
    val nickname: String,
    val email: String,
    val gender: String,
    val age: String
)
