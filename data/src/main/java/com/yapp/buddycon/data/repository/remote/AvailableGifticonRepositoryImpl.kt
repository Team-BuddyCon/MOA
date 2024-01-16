package com.yapp.buddycon.data.repository.remote

import com.yapp.buddycon.data.api.GiftiConService
import com.yapp.buddycon.domain.repository.AvailableGifticonRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AvailableGifticonRepositoryImpl @Inject constructor(
    private val gifticonService: GiftiConService
) : AvailableGifticonRepository {
    override fun getAvailableGifiticon() = flow {
        emit(gifticonService.requestGiftiConDetail(pageNumber = 0, gifticonStoreCategory = null))
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