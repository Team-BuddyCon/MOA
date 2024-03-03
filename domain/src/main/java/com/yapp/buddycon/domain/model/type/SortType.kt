package com.yapp.buddycon.domain.model.type

enum class SortType(val value: String) {
    EXPIRATION_DATE("유효기간순"),
    REGISTRATION("등록순"),
    NAME("이름순");

    fun mapSortTypeToStringValue(): String {
        return when(this) {
            EXPIRATION_DATE -> "EXPIRE_DATE"
            REGISTRATION -> "CREATED_AT"
            NAME -> "NAME"
        }
    }
}
