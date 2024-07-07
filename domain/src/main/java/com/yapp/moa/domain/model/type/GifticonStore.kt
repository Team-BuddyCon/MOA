package com.yapp.moa.domain.model.type

enum class GifticonStore(val value: String, val code: String?) {
    TOTAL("전체", null),
    STARBUCKS("스타벅스", "STARBUCKS"),
    TWOSOME_PLACE("투썸플레이스", "TWOSOME_PLACE"),
    ANGELINUS("엔젤리너스", "ANGELINUS"),
    MEGA_COFFEE("메가커피", "MEGA_COFFEE"),
    COFFEE_BEAN("커피빈", "COFFEE_BEAN"),
    GONG_CHA("공차", "GONG_CHA"),
    BASKIN_ROBBINS("배스킨라빈스", "BASKIN_ROBBINS"),
    MACDONALD("맥도날드", "MACDONALD"),
    GS25("GS25", "GS25"),
    CU("CU", "CU"),
    OTHERS("기타", "OTHERS"),
    NONE("", "")
}

fun mapStringValueToGifticonStore(stringValue: String) =
    when (stringValue) {
        GifticonStore.STARBUCKS.name -> GifticonStore.STARBUCKS
        GifticonStore.TWOSOME_PLACE.name -> GifticonStore.TWOSOME_PLACE
        GifticonStore.ANGELINUS.name -> GifticonStore.ANGELINUS
        GifticonStore.MEGA_COFFEE.name -> GifticonStore.MEGA_COFFEE
        GifticonStore.COFFEE_BEAN.name -> GifticonStore.COFFEE_BEAN
        GifticonStore.GONG_CHA.name -> GifticonStore.GONG_CHA
        GifticonStore.BASKIN_ROBBINS.name -> GifticonStore.BASKIN_ROBBINS
        GifticonStore.MACDONALD.name -> GifticonStore.MACDONALD
        GifticonStore.GS25.name -> GifticonStore.GS25
        GifticonStore.CU.name -> GifticonStore.CU
        else -> GifticonStore.OTHERS
    }
