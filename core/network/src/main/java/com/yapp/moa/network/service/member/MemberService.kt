package com.yapp.moa.network.service.member

import com.yapp.moa.network.service.member.response.LogoutResponse
import retrofit2.http.DELETE
import retrofit2.http.POST

interface MemberService {

    @POST("api/v1/auth/logout")
    suspend fun fetchLogout(): LogoutResponse

    @DELETE("api/v1/users/me")
    suspend fun deleteUser(): DeleteUserResponse
}
