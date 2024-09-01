package com.yapp.moa.data.repository.remote

import com.yapp.moa.domain.model.auth.LoginModel
import com.yapp.moa.domain.repository.AuthRepository
import com.yapp.moa.network.service.auth.AuthService
import com.yapp.moa.network.service.auth.request.LoginRequest
import com.yapp.moa.network.service.auth.request.ReissueRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : AuthRepository {

    override fun fetchLogin(
        oauthAccessToken: String,
        nickname: String,
        email: String,
        gender: String,
        age: String
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
            ).body.toModel()
        )
    }

    override fun fetchReissue(
        accessToken: String,
        refreshToken: String
    ): Flow<LoginModel> = flow {
        emit(
            authService.fetchReissue(
                reissueRequest = ReissueRequest(
                    accessToken = "Bearer $accessToken",
                    refreshToken = refreshToken
                )
            ).body.toModel()
        )
    }
}
