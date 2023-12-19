package com.yapp.buddycon.network.service.auth

import com.yapp.buddycon.network.service.auth.request.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("api/v1/auth/login")
    suspend fun fetchLogin(
        @Body loginRequest: LoginRequest
    )
}