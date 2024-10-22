package com.yapp.moa.network.interceptor

import android.util.Log
import com.yapp.moa.domain.repository.AuthRepository
import com.yapp.moa.domain.repository.TokenRepository
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
        var accessTokenExpiresIn = runBlocking { tokenRepository.getAccessTokenExpiresIn().first() }
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
                accessTokenExpiresIn = token.accessTokenExpiresIn

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

        Log.e("MOATest", "token : Bearer $accessToken expireIn: $accessTokenExpiresIn") // test용 로그 출력 -> 추후 삭제 예정
        return chain.proceed(newRequest)
    }
}
