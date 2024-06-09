package com.yapp.buddycon.domain.model.notification

data class NotificationModel(
    val activated: Boolean = false,
    val fourteenDaysBefore: Boolean = false,
    val sevenDaysBefore: Boolean = false,
    val threeDaysBefore: Boolean = false,
    val oneDayBefore: Boolean = false,
    val theDay: Boolean = false
)
