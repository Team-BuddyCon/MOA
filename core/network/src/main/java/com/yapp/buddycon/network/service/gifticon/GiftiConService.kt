package com.yapp.buddycon.network.service.gifticon

import com.yapp.buddycon.network.service.gifticon.response.AvailableGifticonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GiftiConService {
    @GET("api/v1/gifticons/available")
    suspend fun requestGiftiConDetail(
        @Query("pageNumber") pageNumber: Int, // page
        @Query("gifticonStoreCategory") gifticonStoreCategory: String?, // 기프티콘 가게 카테고리
        @Query("rowCount") rowCount: Int = 20 // page 당 요청 데이터 개수
    ): Response<AvailableGifticonResponse>
}
