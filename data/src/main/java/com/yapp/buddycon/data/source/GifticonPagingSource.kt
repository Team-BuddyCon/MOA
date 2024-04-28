package com.yapp.buddycon.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yapp.buddycon.domain.model.gifticon.AvailableGifticon
import com.yapp.buddycon.domain.model.type.GifticonStore
import com.yapp.buddycon.domain.model.type.GifticonStoreCategory
import com.yapp.buddycon.domain.model.type.SortType
import com.yapp.buddycon.network.service.gifticon.GiftiConService
import javax.inject.Inject

private const val PAGING_START_KEY = 0
const val PAGING_SIZE = 20

class GifticonPagingSource @Inject constructor(
    val giftiConService: GiftiConService,
    val gifticonStoreCategory: GifticonStoreCategory?,
    val gifticonStore: GifticonStore?,
    val gifticonSortType: SortType?
) : PagingSource<Int, AvailableGifticon.AvailableGifticonInfo>() {
    override fun getRefreshKey(state: PagingState<Int, AvailableGifticon.AvailableGifticonInfo>): Int? {
        return state.anchorPosition?.let { position ->
            val anchorPage = state.closestPageToPosition(position)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AvailableGifticon.AvailableGifticonInfo> {
        try {
            val pageKey = params.key ?: PAGING_START_KEY
            val response = giftiConService.requestAvailableGiftiCons(
                pageNumber = pageKey,
                rowCount = PAGING_SIZE,
                gifticonStoreCategory = gifticonStoreCategory?.mapGifiticonStoreCategoryToStringValue(),
                gifticonStore = gifticonStore?.code,
                gifticonSortType = gifticonSortType?.mapSortTypeToStringValue()
            )

            if (response.isSuccessful) {
                response.body()?.let { response ->
                    val items = response.body.content.map { it.mapToAvailableGifticonInfo() }
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
