package com.yapp.buddycon.domain.repository

import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    fun isFirstInstallation(): Flow<Boolean>
    suspend fun checkFirstInstallation()

    fun getNickname(): Flow<String>

    suspend fun saveNickname(nickname: String)
}
