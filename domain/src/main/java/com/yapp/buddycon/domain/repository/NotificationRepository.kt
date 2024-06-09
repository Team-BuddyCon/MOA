package com.yapp.buddycon.domain.repository

import com.yapp.buddycon.domain.model.notification.NotificationModel
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getNotificationSettings(): Flow<NotificationModel>

    fun updateNotificationSettings(
        activated: Boolean,
        fourteenDaysBefore: Boolean,
        sevenDaysBefore: Boolean,
        threeDaysBefore: Boolean,
        oneDayBefore: Boolean,
        theDay: Boolean
    ): Flow<Boolean>
}
