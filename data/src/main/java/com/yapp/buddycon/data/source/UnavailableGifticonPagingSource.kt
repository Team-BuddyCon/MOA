package com.yapp.buddycon.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yapp.buddycon.domain.model.gifticon.UnavailableGifticon
import com.yapp.buddycon.network.service.gifticon.GiftiConService
import javax.inject.Inject

private const val PAGING_START_KEY = 0

class UnavailableGifticonPagingSource @Inject constructor(
    val giftiConService: GiftiConService,
) : PagingSource<Int, UnavailableGifticon.UnavailableGifticonInfo>() {
    override fun getRefreshKey(state: PagingState<Int, UnavailableGifticon.UnavailableGifticonInfo>): Int? {
        return state.anchorPosition?.let { position ->
            val anchorPage = state.closestPageToPosition(position)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnavailableGifticon.UnavailableGifticonInfo> {
        try {
            val pageKey = params.key ?: PAGING_START_KEY
            val response = giftiConService.requestUnavailableGiftiCons(
                pageNumber = pageKey,
                rowCount = PAGING_SIZE
            )

            if (response.isSuccessful) {
                response.body()?.let { response ->
                    val items = response.body.content.map { it.mapToUnavailableGifticonInfo() }
                    return LoadResult.Page(
                        items,
                        prevKey = null,
                        nextKey = if (items.isEmpty()) null else pageKey + 1
                    )
                }
            }
            return LoadResult.Error(IllegalStateException())
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}
