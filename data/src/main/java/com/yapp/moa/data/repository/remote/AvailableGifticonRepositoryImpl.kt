package com.yapp.moa.data.repository.remote

import com.yapp.moa.domain.model.type.GifticonStoreCategory
import com.yapp.moa.domain.model.type.SortType
import com.yapp.moa.network.service.gifticon.GiftiConService
import com.yapp.moa.domain.repository.AvailableGifticonRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AvailableGifticonRepositoryImpl @Inject constructor(
    private val gifticonService: GiftiConService
) : AvailableGifticonRepository {
    override fun getAvailableGifiticon(
        gifticonStoreCategory: GifticonStoreCategory,
        gifticonSortType: SortType,
        currentPage: Int
    ) = flow {
        emit(
            gifticonService.requestAvailableGiftiCons(
                pageNumber = currentPage,
                gifticonStoreCategory = gifticonStoreCategory.mapGifiticonStoreCategoryToStringValue(),
                gifticonSortType = gifticonSortType.mapSortTypeToStringValue()
            )
        )
    }.catch { error ->
        throw Throwable("[getAvailableGifiticon] catch error!", error)
    }.map { response ->
        if (response.isSuccessful) {
            (response.body() ?: throw NullPointerException("null response")).body.mapToAvailableGifticon()
        } else {
            throw Throwable("error.. msg : ${response.message()}")
        }
    }
}
