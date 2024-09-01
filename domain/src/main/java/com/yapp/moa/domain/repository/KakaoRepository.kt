package com.yapp.moa.domain.repository

import com.yapp.moa.domain.model.kakao.SearchPlaceModel
import kotlinx.coroutines.flow.Flow

interface KakaoRepository {
    fun searchPlacesByKeyword(
        query: String,
        x: String,
        y: String,
        radius: Int
    ): Flow<List<SearchPlaceModel>>
}
