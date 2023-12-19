package com.yapp.buddycon.data.repository

import com.yapp.buddycon.domain.repository.AuthRepository
import com.yapp.buddycon.network.service.auth.AuthService
import com.yapp.buddycon.network.service.auth.request.LoginRequest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : AuthRepository {

    override fun fetchLogin(
        oauthAccessToken: String,
        nickname: String,
        email: String?,
        gender: String?,
        age: String?
    ) = flow {
        emit(
            authService.fetchLogin(
                loginRequest = LoginRequest(
                    oauthAccessToken = oauthAccessToken,
                    nickname = nickname,
                    email = email,
                    gender = gender,
                    age = age
                )
            )
        )
    }
}
