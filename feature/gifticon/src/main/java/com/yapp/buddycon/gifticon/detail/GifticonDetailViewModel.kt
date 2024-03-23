package com.yapp.buddycon.gifticon.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.model.gifticon.GifticonDetailModel
import com.yapp.buddycon.domain.model.kakao.SearchPlaceModel
import com.yapp.buddycon.domain.repository.GifticonRepository
import com.yapp.buddycon.domain.repository.KakaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GifticonDetailViewModel @Inject constructor(
    private val gifticonRepository: GifticonRepository,
    private val kakaoRepository: KakaoRepository
) : ViewModel() {
    private val _gifticonDetailModel = MutableStateFlow<GifticonDetailModel>(GifticonDetailModel())
    val gifticonDetailModel = _gifticonDetailModel.asStateFlow()

    private val _searchPlacesModel = MutableStateFlow<List<SearchPlaceModel>>(listOf())
    val searchPlacesModel = _searchPlacesModel.asStateFlow()

    fun requestGifticonDetail(gifticonId: Int) {
        gifticonRepository.requestGifticonDetail(gifticonId)
            .onEach { _gifticonDetailModel.value = it }
            .launchIn(viewModelScope)
    }

    fun searchPlacesByKeyword(
        query: String,
        x: String,
        y: String
    ) {
        kakaoRepository.searchPlacesByKeyword(
            query = query,
            x = x,
            y = y,
            radius = 2000
        ).onEach { _searchPlacesModel.value = it }
            .launchIn(viewModelScope)
    }
}
