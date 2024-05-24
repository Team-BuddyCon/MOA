package com.yapp.buddycon.domain.model.type

enum class GifticonStoreCategory(val value: String) {
    TOTAL("전체"),
    CAFE("카페"),
    CONVENIENCE_STORE("편의점"),
    OTHERS("기타");

    fun mapGifiticonStoreCategoryToStringValue(): String? {
        return when (this) {
            TOTAL -> null
            CAFE -> "CAFE"
            CONVENIENCE_STORE -> "CONVENIENCE_STORE"
            OTHERS -> "OTHERS"
        }
    }
}

fun mapStringValueToGifiticonStoreCategory(string: String?) =
    when (string) {
        "CAFE" -> GifticonStoreCategory.CAFE
        "CONVENIENCE_STORE" -> GifticonStoreCategory.CONVENIENCE_STORE
        "OTHERS" -> GifticonStoreCategory.OTHERS
        else -> GifticonStoreCategory.TOTAL
    }
