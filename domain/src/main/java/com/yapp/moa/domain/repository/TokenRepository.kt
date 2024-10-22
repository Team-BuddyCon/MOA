package com.yapp.moa.domain.repository

import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    fun isFirstInstallation(): Flow<Boolean>
    suspend fun checkFirstInstallation()

    fun isShouldShowCoachMark(): Flow<Boolean>
    suspend fun stopShowCoachMark()

    fun isFirstExternalStoragePopup(): Flow<Boolean>
    suspend fun checkFirstExternalStoragePopup()

    fun getNickname(): Flow<String>
    suspend fun saveNickname(nickname: String)

    fun getAccessToken(): Flow<String>
    suspend fun saveAccessToken(accessToken: String)

    fun getRefreshToken(): Flow<String>
    suspend fun saveRefreshToken(refreshToken: String)

    fun getAccessTokenExpiresIn(): Flow<Long>
    suspend fun saveAccessTokenExpiresIn(expire: Long)

    fun isTestMode(): Flow<Boolean>
    suspend fun saveTestMode(isTestMode: Boolean)
}
