package com.yapp.moa.domain.repository

import com.yapp.moa.domain.model.notification.NotificationModel
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
