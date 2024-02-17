package com.yapp.buddycon.domain.repository

import com.yapp.buddycon.domain.model.auth.LoginModel
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun fetchLogin(
        oauthAccessToken: String,
        nickname: String,
        email: String,
        gender: String,
        age: String
    ): Flow<LoginModel>

    fun fetchReissue(
        accessToken: String,
        refreshToken: String
    ): Flow<LoginModel>
}
