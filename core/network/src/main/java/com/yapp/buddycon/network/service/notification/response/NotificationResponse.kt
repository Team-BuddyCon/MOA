package com.yapp.buddycon.network.service.notification.response

import com.google.gson.annotations.SerializedName
import com.yapp.buddycon.domain.model.notification.NotificationModel

data class NotificationResponse(
    val status: Int,
    val message: String,
    @SerializedName("body") val state: NotificationStateResponse
)

data class NotificationStateResponse(
    val activated: Boolean,
    val fourteenDaysBefore: Boolean,
    val sevenDaysBefore: Boolean,
    val threeDaysBefore: Boolean,
    val oneDayBefore: Boolean,
    val theDay: Boolean
) {
    fun toModel() = NotificationModel(
        activated = activated,
        fourteenDaysBefore = fourteenDaysBefore,
        sevenDaysBefore = sevenDaysBefore,
        threeDaysBefore = threeDaysBefore,
        oneDayBefore = oneDayBefore,
        theDay = theDay
    )
}
