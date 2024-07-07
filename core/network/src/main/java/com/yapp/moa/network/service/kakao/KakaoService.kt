package com.yapp.moa.network.service.kakao

import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoService {

    @GET("local/search/keyword")
    suspend fun searchPlacesByKeyword(
        @Query("query") query: String,
        @Query("x") x: String,
        @Query("y") y: String,
        @Query("radius") radius: Int
    ): SearchPlacesResponse
}
