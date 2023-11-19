package com.yapp.buddycon.domain.model.gifticon

import com.yapp.buddycon.domain.model.type.GifticonCategory
import java.text.SimpleDateFormat

private const val DATE_FORMAT = "yyyy.MM.dd"

data class GifticonModel(
    val imageUrl: String = "",
    val category: GifticonCategory = GifticonCategory.ETC,
    val name: String = "",
    val expirationTime: Long = 0L
) {
    @Suppress("SimpleDateFormat")
    fun toExpirationDate(): String = SimpleDateFormat(DATE_FORMAT).format(expirationTime)
}
