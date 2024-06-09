package com.yapp.buddycon.network.service.notification.request

data class NotificationRequest(
    val activated: Boolean,
    val fourteenDaysBefore: Boolean,
    val sevenDaysBefore: Boolean,
    val threeDaysBefore: Boolean,
    val oneDayBefore: Boolean,
    val theDay: Boolean
)
