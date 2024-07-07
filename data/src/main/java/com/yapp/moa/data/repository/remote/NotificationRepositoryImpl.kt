package com.yapp.moa.data.repository.remote

import com.yapp.moa.domain.model.notification.NotificationModel
import com.yapp.moa.domain.repository.NotificationRepository
import com.yapp.moa.network.service.notification.NotificationService
import com.yapp.moa.network.service.notification.request.NotificationRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationService: NotificationService
) : NotificationRepository {

    override fun getNotificationSettings(): Flow<NotificationModel> = flow {
        emit(
            notificationService
                .getNotificationSettings()
                .state
                .toModel()
        )
    }

    override fun updateNotificationSettings(
        activated: Boolean,
        fourteenDaysBefore: Boolean,
        sevenDaysBefore: Boolean,
        threeDaysBefore: Boolean,
        oneDayBefore: Boolean,
        theDay: Boolean
    ): Flow<Boolean> = flow {
        emit(
            notificationService
                .updateNotificationSettings(
                    request = NotificationRequest(
                        activated = activated,
                        fourteenDaysBefore = fourteenDaysBefore,
                        sevenDaysBefore = sevenDaysBefore,
                        threeDaysBefore = threeDaysBefore,
                        oneDayBefore = oneDayBefore,
                        theDay = theDay
                    )
                ).statue == 200
        )
    }
}
