package com.yapp.buddycon.domain.model.auth

data class LoginModel(
    val accessToken: String = "",
    val refreshToken: String = "",
    val accessTokenExpiresIn: Long = Long.MAX_VALUE,
    val isFirstLogin: Boolean = true
)
