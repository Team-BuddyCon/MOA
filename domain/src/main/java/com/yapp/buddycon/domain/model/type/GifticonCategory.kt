package com.yapp.buddycon.domain.model.type

import java.lang.IllegalStateException

enum class GifticonCategory(val value: String) {
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

fun convertGifticonCategory(name: String) =
    when (name) {
        GifticonCategory.STARBUCKS.value -> GifticonCategory.STARBUCKS
        GifticonCategory.TWOSOME_PLACE.value -> GifticonCategory.TWOSOME_PLACE
        GifticonCategory.ANGELINUS.value -> GifticonCategory.ANGELINUS
        GifticonCategory.MEGA_COFFEE.value -> GifticonCategory.MEGA_COFFEE
        GifticonCategory.COFFEE_BEAN.value -> GifticonCategory.COFFEE_BEAN
        GifticonCategory.GONG_CHA.value -> GifticonCategory.GONG_CHA
        GifticonCategory.BASKINROBBINS.value -> GifticonCategory.BASKINROBBINS
        GifticonCategory.MCDONALD.value -> GifticonCategory.MCDONALD
        GifticonCategory.GS25.value -> GifticonCategory.GS25
        GifticonCategory.CU.value -> GifticonCategory.CU
        else -> throw IllegalStateException()
    }

fun convertStringValueToGifticonCategory(stringValue: String) =
    when (stringValue) {
        GifticonCategory.STARBUCKS.name -> GifticonCategory.STARBUCKS
        GifticonCategory.TWOSOME_PLACE.name -> GifticonCategory.TWOSOME_PLACE
        GifticonCategory.ANGELINUS.name -> GifticonCategory.ANGELINUS
        GifticonCategory.MEGA_COFFEE.name -> GifticonCategory.MEGA_COFFEE
        GifticonCategory.COFFEE_BEAN.name -> GifticonCategory.COFFEE_BEAN
        GifticonCategory.GONG_CHA.name -> GifticonCategory.GONG_CHA
        GifticonCategory.BASKINROBBINS.name -> GifticonCategory.BASKINROBBINS
        GifticonCategory.MCDONALD.name -> GifticonCategory.MCDONALD
        GifticonCategory.GS25.name -> GifticonCategory.GS25
        GifticonCategory.CU.name -> GifticonCategory.CU
        else -> GifticonCategory.ETC
    }
