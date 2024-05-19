package com.yapp.buddycon.gifticon.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.model.gifticon.GifticonDetailModel
import com.yapp.buddycon.domain.repository.GifticonRepository
import com.yapp.buddycon.domain.repository.KakaoRepository
import com.yapp.buddycon.utility.stability.MapSearchPlace
import com.yapp.buddycon.domain.result.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GifticonDetailViewModel @Inject constructor(
    private val gifticonRepository: GifticonRepository,
    private val kakaoRepository: KakaoRepository
) : ViewModel() {

    private val _gifticonDetailModel = MutableStateFlow<GifticonDetailModel>(GifticonDetailModel())
    val gifticonDetailModel = _gifticonDetailModel.asStateFlow()

    private val _mapSearchPlace = MutableStateFlow<MapSearchPlace>(MapSearchPlace())
    val mapSearchPlace = _mapSearchPlace.asStateFlow()

    private var _updateGifticonUsedStateDataResult = MutableStateFlow<DataResult<Boolean>>(DataResult.None)
    val updateGifticonUsedStateDataResult = _updateGifticonUsedStateDataResult.asStateFlow()

    private var _isUsedGifticon = MutableStateFlow(false)
    val isUsedGifticon = _isUsedGifticon.asStateFlow()

    fun requestGifticonDetail(gifticonId: Int) {
        gifticonRepository.requestGifticonDetail(gifticonId)
            .onEach {
                _gifticonDetailModel.value = it
                updateGiftionUsedStateValue(it.used)
            }
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
        ).onEach {
            _mapSearchPlace.value = _mapSearchPlace.value.copy(
                searchPlaceModels = it
            )
        }.launchIn(viewModelScope)
    }

    fun updateGifticonUsedState(gifticonId: Int, newUsedState: Boolean) { // 기프티콘 상세 정보 조회 api 수정 후 사용 예정
        viewModelScope.launch {
            gifticonRepository.updateGifticonUsedState(
                gifticonId = gifticonId,
                used = newUsedState
            ).onStart {
                _updateGifticonUsedStateDataResult.value = DataResult.Loading
            }.catch { throwable ->
                throwable.stackTrace.forEach {
                    Log.e("MOATest", "[stackTrace] : $it")
                }
                _updateGifticonUsedStateDataResult.value = DataResult.Failure(throwable = throwable)
            }.collectLatest {
                getGifticonUsedState(gifticonId = gifticonId)
            }
        }
    }

    private fun getGifticonUsedState(gifticonId: Int) {
        viewModelScope.launch {
            gifticonRepository.requestGifticonDetail(gifticonId)
                .catch { throwable ->
                    throwable.stackTrace.forEach {
                        Log.e("MOATest", "[stackTrace] : $it")
                    }
                    _updateGifticonUsedStateDataResult.value = DataResult.Failure(throwable = throwable)
                }
                .collectLatest { gifticonDetailModel ->
                    _updateGifticonUsedStateDataResult.value = DataResult.Success(data = gifticonDetailModel.used)
                }
        }
    }

    fun updateGiftionUsedStateValue(isUsed: Boolean) {
        _isUsedGifticon.update { isUsed }
    }
}
