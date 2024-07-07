package com.yapp.moa.data.repository.remote

import android.content.Context
import android.net.Uri
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.Gson
import com.yapp.moa.data.source.GifticonPagingSource
import com.yapp.moa.data.source.PAGING_SIZE
import com.yapp.moa.data.source.UnavailableGifticonPagingSource
import com.yapp.moa.domain.model.gifticon.AvailableGifticon
import com.yapp.moa.domain.model.gifticon.GifticonDetailModel
import com.yapp.moa.domain.model.gifticon.UnavailableGifticon
import com.yapp.moa.domain.model.type.GifticonStore
import com.yapp.moa.domain.model.type.GifticonStoreCategory
import com.yapp.moa.domain.model.type.SortType
import com.yapp.moa.domain.repository.GifticonRepository
import com.yapp.moa.network.service.gifticon.GiftiConService
import com.yapp.moa.network.service.gifticon.request.CreateGifticonRequest
import com.yapp.moa.network.service.gifticon.request.EditGifticonRequest
import com.yapp.moa.utility.getAbsolutePath
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class GifticonRepositoryImpl @Inject constructor(
    private val giftiConService: GiftiConService,
    @ApplicationContext private val context: Context,
) : GifticonRepository {

    override fun createGifticon(
        imagePath: String,
        name: String,
        expireDate: String,
        store: String,
        memo: String
    ) = flow {
        val uri = Uri.parse(imagePath)
        val imageFile = File(uri.getAbsolutePath(context))
        val imageRequestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val imageMultipartBody = MultipartBody.Part.createFormData("image", imageFile.name, imageRequestBody)

        val requestBody = Gson().toJson(
            CreateGifticonRequest(
                name = name,
                expireDate = expireDate,
                store = store,
                memo = memo
            )
        ).toRequestBody("application/json".toMediaTypeOrNull())

        emit(
            giftiConService.createGifticon(
                image = imageMultipartBody,
                dto = requestBody
            ).body
        )
    }

    override fun requestGifticonDetail(gifticonId: Int): Flow<GifticonDetailModel> = flow {
        emit(
            giftiConService.getGifticonDetail(gifticonId = gifticonId)
                .body
                .toModel()
        )
    }

    override fun fetchAvailableGifticon(
        gifticonStoreCategory: GifticonStoreCategory?,
        gifticonStore: GifticonStore?,
        gifticonSortType: SortType?
    ): Flow<PagingData<AvailableGifticon.AvailableGifticonInfo>> {
        return Pager(
            config = PagingConfig(pageSize = PAGING_SIZE),
            pagingSourceFactory = {
                GifticonPagingSource(
                    giftiConService = giftiConService,
                    gifticonStoreCategory = gifticonStoreCategory,
                    gifticonStore = gifticonStore,
                    gifticonSortType = gifticonSortType
                )
            }
        ).flow
    }

    override fun fetchUnavailableGifticon(): Flow<PagingData<UnavailableGifticon.UnavailableGifticonInfo>> {
        return Pager(
            config = PagingConfig(pageSize = PAGING_SIZE),
            pagingSourceFactory = {
                UnavailableGifticonPagingSource(
                    giftiConService = giftiConService
                )
            }
        ).flow
    }

    override fun getGifticonCount(
        used: Boolean,
        gifticonStoreCategory: GifticonStoreCategory?,
        gifticonStore: GifticonStore?,
        remainingDays: Int?
    ): Flow<Int> = flow {
        emit(
            giftiConService.getGifticonCount(
                used = used,
                gifticonStoreCategory = gifticonStoreCategory?.name,
                gifticonStore = gifticonStore?.code,
                remainingDays = remainingDays
            ).body.count
        )
    }

    override fun editGifticonDetail(
        gifticonId: Int,
        name: String,
        expireDate: String,
        gifticonStore: String,
        memo: String
    ) = flow {
        emit(
            giftiConService.editGifticonDetail(
                gifticonId = gifticonId,
                editGifticonRequest = EditGifticonRequest(
                    name = name,
                    expireDate = expireDate,
                    store = gifticonStore,
                    memo = memo
                )
            )
        )
    }.catch { error ->
        throw Throwable("[editGifticonDetail] catch error!", error)
    }.map { response ->
        if (response.isSuccessful) {
            (response.body() ?: throw NullPointerException("null response")).mapToUnit()
        } else {
            throw Throwable("error.. msg : ${response.message()}")
        }
    }

    override fun updateGifticonUsedState(
        gifticonId: Int,
        used: Boolean
    ) = flow {
        emit(
            giftiConService.updateGifticonUsedState(
                gifticonId = gifticonId,
                used = used
            )
        )
    }.catch { error ->
        throw Throwable("[updateGifticonUsedState] catch error!", error)
    }.map { response ->
        if (response.isSuccessful) {
            (response.body() ?: throw NullPointerException("null response")).mapToUnit()
        } else {
            throw Throwable("error.. msg : ${response.message()}")
        }
    }

    override fun deleteGifticon(
        gifticonId: Int,
    ) = flow {
        emit(
            giftiConService.deleteGifticon(gifticonId = gifticonId)
        )
    }.catch { error ->
        throw Throwable("[deleteGifticon] catch error!", error)
    }.map { response ->
        if (response.isSuccessful) {
            (response.body() ?: throw NullPointerException("null response")).mapToUnit()
        } else {
            throw Throwable("error.. msg : ${response.message()}")
        }
    }
}
