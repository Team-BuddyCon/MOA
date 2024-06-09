package com.yapp.buddycon.network.service.member

import com.yapp.buddycon.network.service.member.response.LogoutResponse
import retrofit2.http.POST

interface MemberService {

    @POST("api/v1/auth/logout")
    suspend fun fetchLogout(): LogoutResponse
}
