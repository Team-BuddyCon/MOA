package com.yapp.buddycon.domain.repository

import com.yapp.buddycon.domain.model.kakao.SearchPlaceModel
import kotlinx.coroutines.flow.Flow

interface KakaoRepository {
    fun searchPlacesByKeyword(
        query: String,
        x: String,
        y: String,
        radius: Int
    ): Flow<List<SearchPlaceModel>>
}
