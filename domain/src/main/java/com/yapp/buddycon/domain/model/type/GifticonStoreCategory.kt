package com.yapp.buddycon.domain.model.type

enum class GifticonStoreCategory(val value: String) {
    TOTAL("전체"),
    CAFE("카페"),
    CONVENIENCESTORE("편의점"),
    OTHERS("기타");

    fun mapGifiticonStoreCategoryToStringValue(): String? {
        return when(this) {
            TOTAL -> null
            CAFE -> "CAFE"
            CONVENIENCESTORE -> "CONVENIENCE_STORE"
            OTHERS -> "OTHERS"
        }
    }
}
