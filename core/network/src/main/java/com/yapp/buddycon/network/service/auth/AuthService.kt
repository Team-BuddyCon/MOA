package com.yapp.buddycon.network.service.auth

import com.yapp.buddycon.network.service.auth.request.AuthRequest
import com.yapp.buddycon.network.service.auth.response.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("api/v1/auth/login")
    suspend fun fetchLogin(
        @Body authRequest: AuthRequest
    ): AuthResponse
}
