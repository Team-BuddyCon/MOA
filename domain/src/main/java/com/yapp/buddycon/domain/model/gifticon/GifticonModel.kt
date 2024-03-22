package com.yapp.buddycon.domain.model.gifticon

import com.yapp.buddycon.domain.model.type.GifticonStore
import java.text.SimpleDateFormat

private const val DATE_FORMAT = "yyyy.MM.dd"

data class GifticonModel(
    val imageUrl: String = "",
    val category: GifticonStore = GifticonStore.ETC,
    val name: String = "",
    val expirationTime: Long = 0L
) {
    @Suppress("SimpleDateFormat")
    fun toExpirationDate(): String = SimpleDateFormat(DATE_FORMAT).format(expirationTime)
}
