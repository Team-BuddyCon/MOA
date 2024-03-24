package com.yapp.buddycon.domain.model.type

enum class GifticonStore(val value: String) {
    TOTAL("전체"),
    STARBUCKS("스타벅스"),
    TWOSOME_PLACE("투썸플레이스"),
    ANGELINUS("엔젤리너스"),
    MEGA_COFFEE("메가커피"),
    COFFEE_BEAN("커피빈"),
    GONG_CHA("공차"),
    BASKINROBBINS("배스킨라빈스"),
    MCDONALD("맥도날드"),
    GS25("GS25"),
    CU("CU"),
    ETC("기타")
}

fun convertStringValueToGifticonStore(stringValue: String) =
    when (stringValue) {
        GifticonStore.STARBUCKS.name -> GifticonStore.STARBUCKS
        GifticonStore.TWOSOME_PLACE.name -> GifticonStore.TWOSOME_PLACE
        GifticonStore.ANGELINUS.name -> GifticonStore.ANGELINUS
        GifticonStore.MEGA_COFFEE.name -> GifticonStore.MEGA_COFFEE
        GifticonStore.COFFEE_BEAN.name -> GifticonStore.COFFEE_BEAN
        GifticonStore.GONG_CHA.name -> GifticonStore.GONG_CHA
        GifticonStore.BASKINROBBINS.name -> GifticonStore.BASKINROBBINS
        GifticonStore.MCDONALD.name -> GifticonStore.MCDONALD
        GifticonStore.GS25.name -> GifticonStore.GS25
        GifticonStore.CU.name -> GifticonStore.CU
        else -> GifticonStore.ETC
    }
