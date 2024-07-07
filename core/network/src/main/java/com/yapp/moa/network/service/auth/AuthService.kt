package com.yapp.moa.network.service.auth

import com.yapp.moa.network.service.auth.request.LoginRequest
import com.yapp.moa.network.service.auth.request.ReissueRequest
import com.yapp.moa.network.service.auth.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("api/v1/auth/login")
    suspend fun fetchLogin(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @POST("api/v1/auth/reissue")
    suspend fun fetchReissue(
        @Body reissueRequest: ReissueRequest
    ): LoginResponse
}
