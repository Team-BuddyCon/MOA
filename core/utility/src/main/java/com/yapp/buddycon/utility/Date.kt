package com.yapp.buddycon.utility

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.isDeadLine(): Boolean {
    val today = Date()
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).parse(this) ?: Date()
    val diff = (date.time - today.time) / (1000 * 60 * 60 * 24)
    return diff in 0..14
}
