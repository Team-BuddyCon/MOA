package com.yapp.buddycon.network.service.auth.response

import com.yapp.buddycon.domain.model.auth.AuthModel

data class AuthResponse(
    val status: Int,
    val message: String,
    val body: AuthResponseBody
)

data class AuthResponseBody(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiresIn: Long
) {
    fun toModel() = AuthModel(
        accessToken = accessToken,
        refreshToken = refreshToken,
        accessTokenExpiresIn = accessTokenExpiresIn
    )
}
