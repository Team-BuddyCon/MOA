package com.yapp.buddycon.domain.repository

import com.yapp.buddycon.domain.model.auth.AuthModel
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun fetchLogin(
        oauthAccessToken: String,
        nickname: String,
        email: String,
        gender: String,
        age: String
    ): Flow<AuthModel>
}
