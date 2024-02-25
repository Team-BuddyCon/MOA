package com.yapp.buddycon.data.repository.remote

import android.content.Context
import android.net.Uri
import com.google.gson.Gson
import com.yapp.buddycon.domain.repository.GifticonRepository
import com.yapp.buddycon.network.service.gifticon.GiftiConService
import com.yapp.buddycon.network.service.gifticon.request.CreateGifticonRequest
import com.yapp.buddycon.utility.getAbsolutePath
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
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
            ).statue == 200
        )
    }
}
