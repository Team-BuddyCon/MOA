package com.yapp.buddycon.network.service.gifticon

import com.yapp.buddycon.network.service.gifticon.response.AvailableGifticonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GiftiConService {
    @GET("api/v1/gifticons/available")
    suspend fun requestGiftiConDetail(
        @Header("Authorization") token: String = "Bearer " +
            "eyJhbGciOiJIUzUxMiJ9.eyJpZCI6MiwiaWF0IjoxNzAyNzg5NDc2LCJleHAiOjE3MDg4Mzc0NzZ9." +
            "8YPrIlLexzGiqHwE1T_n2E-hCYbsNqJA5kUPWwgD0H8GmrGGsMgexme4NnNzBgsiHWG2uGtDLZL9fDCdiyZNUw",
        @Query("pageNumber") pageNumber: Int, // page
        @Query("gifticonStoreCategory") gifticonStoreCategory: String?, // 기프티콘 가게 카테고리
        @Query("rowCount") rowCount: Int = 20 // page 당 요청 데이터 개수
    ): Response<AvailableGifticonResponse>
}
