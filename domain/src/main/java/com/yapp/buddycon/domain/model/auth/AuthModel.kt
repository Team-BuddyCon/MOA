package com.yapp.buddycon.domain.model.auth

data class AuthModel(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiresIn: Long
)
