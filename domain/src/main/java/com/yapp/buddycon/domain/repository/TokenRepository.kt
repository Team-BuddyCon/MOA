package com.yapp.buddycon.domain.repository

import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    fun isFirstInstallation(): Flow<Boolean>
    suspend fun checkFirstInstallation()

    fun getNickname(): Flow<String>

    suspend fun saveNickname(nickname: String)

    fun getAccessToken(): Flow<String>

    suspend fun saveAccessToken(accessToken: String)

    fun getRefreshToken(): Flow<String>

    suspend fun saveRefreshToken(refreshToken: String)

    fun getAccessTokenExpiresIn(): Flow<Long>

    suspend fun saveAccessTokenExpiresIn(expire: Long)
}
