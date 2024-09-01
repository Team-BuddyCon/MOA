package com.yapp.moa.domain.repository

import com.yapp.moa.domain.model.auth.LoginModel
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
