package com.yapp.buddycon.network.service.auth.response

import com.yapp.buddycon.domain.model.auth.LoginModel

data class LoginResponse(
    val status: Int,
    val message: String,
    val body: LoginResponseBody
)

data class LoginResponseBody(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiresIn: Long,
    val isFirstLogin: Boolean
) {
    fun toModel() = LoginModel(
        accessToken = accessToken,
        refreshToken = refreshToken,
        accessTokenExpiresIn = accessTokenExpiresIn,
        isFirstLogin = isFirstLogin
    )
}
