package com.yapp.buddycon.network.interceptor

import com.yapp.buddycon.domain.repository.AuthRepository
import com.yapp.buddycon.domain.repository.TokenRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class BuddyConInterceptor @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var accessToken = runBlocking { tokenRepository.getAccessToken().first() }
        val refreshToken = runBlocking { tokenRepository.getRefreshToken().first() }
        val accessTokenExpiresIn = runBlocking { tokenRepository.getAccessTokenExpiresIn().first() }
        val currentTime = System.currentTimeMillis()

        if (accessTokenExpiresIn < currentTime) {
            try {
                val token = runBlocking {
                    authRepository.fetchReissue(
                        accessToken = accessToken,
                        refreshToken = refreshToken
                    ).first()
                }
                accessToken = token.accessToken
                runBlocking {
                    tokenRepository.saveAccessToken(accessToken)
                    tokenRepository.saveRefreshToken(refreshToken)
                    tokenRepository.saveAccessTokenExpiresIn(accessTokenExpiresIn)
                }
            } catch (e: Exception) {
            }
        }

        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()
        return chain.proceed(newRequest)
    }
}
