package com.yapp.buddycon.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun fetchLogin(
        oauthAccessToken: String,
        nickname: String,
        email: String? = null,
        gender: String? = null,
        age: String? = null
    ): Flow<Unit>
}