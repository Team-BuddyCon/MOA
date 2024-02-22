package com.yapp.buddycon.data.repository.remote

import com.yapp.buddycon.domain.model.type.GifticonStoreCategory
import com.yapp.buddycon.network.service.gifticon.GiftiConService
import com.yapp.buddycon.domain.repository.AvailableGifticonRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AvailableGifticonRepositoryImpl @Inject constructor(
    private val gifticonService: GiftiConService
) : AvailableGifticonRepository {
    override fun getAvailableGifiticon(
        gifticonStoreCategory: GifticonStoreCategory,
        currentPage: Int
    ) = flow {
        emit(
            gifticonService.requestAvailableGiftiCons(
                pageNumber = currentPage,
                gifticonStoreCategory = gifticonStoreCategory.mapToStringValueForRequest()
            )
        )
    }.catch { error ->
        throw Throwable("catch error!", error)
    }.map { response ->
        if (response.isSuccessful) {
            (response.body() ?: throw NullPointerException("null response")).body.mapToAvailableGifticon()
        } else {
            throw Throwable("error.. msg : ${response.message()}")
        }
    }
}
