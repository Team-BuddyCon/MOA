package com.yapp.moa.network.service.notification

import com.yapp.moa.network.service.notification.request.NotificationRequest
import com.yapp.moa.network.service.notification.response.NotificationResponse
import com.yapp.moa.network.service.notification.response.UpdateNotificationResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface NotificationService {

    @GET("api/v1/notification-settings/me")
    suspend fun getNotificationSettings(): NotificationResponse

    @PUT("api/v1/notification-settings/me")
    suspend fun updateNotificationSettings(
        @Body request: NotificationRequest
    ): UpdateNotificationResponse
}
