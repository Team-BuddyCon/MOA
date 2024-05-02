package com.yapp.buddycon.data.repository.remote

import com.yapp.buddycon.domain.model.kakao.SearchPlaceModel
import com.yapp.buddycon.domain.repository.KakaoRepository
import com.yapp.buddycon.network.service.kakao.KakaoService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class KakaoRepositoryImpl @Inject constructor(
    private val kakaoService: KakaoService
) : KakaoRepository {
    override fun searchPlacesByKeyword(query: String, x: String, y: String, radius: Int): Flow<List<SearchPlaceModel>> = flow {
        emit(
            kakaoService.searchPlacesByKeyword(
                query = query,
                x = x,
                y = y,
                radius = radius
            ).places
                .map { it.toModel(store = query) }
        )
    }
}
