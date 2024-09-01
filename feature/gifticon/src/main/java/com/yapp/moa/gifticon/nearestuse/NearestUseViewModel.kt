package com.yapp.moa.gifticon.nearestuse

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.moa.domain.model.gifticon.GifticonDetailModel
import com.yapp.moa.domain.model.kakao.SearchPlaceModel
import com.yapp.moa.domain.repository.GifticonRepository
import com.yapp.moa.domain.repository.KakaoRepository
import com.yapp.moa.domain.result.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class NearestUseViewModel @Inject constructor(
    private val gifticonRepository: GifticonRepository,
    private val kakaoRepository: KakaoRepository
) : ViewModel() {
    private val _gifticonDetailModel = MutableStateFlow<GifticonDetailModel>(GifticonDetailModel())
    val gifticonDetailModel = _gifticonDetailModel.asStateFlow()

    private var _searchPlacesDataResult = MutableStateFlow<DataResult<List<SearchPlaceModel>>>(
        DataResult.None
    )
    val searchPlacesDataResult = _searchPlacesDataResult.asStateFlow()

    private var _nearestUseScreenUiState = MutableStateFlow<NearestUseScreenUiState>(NearestUseScreenUiState.None)
    val nearestUseScreenUiState = _nearestUseScreenUiState.asStateFlow()

    private var _uiStateFromSearchPlacesDataResult = MutableStateFlow<UiStateFromSearchPlacesDataResult>(UiStateFromSearchPlacesDataResult.None)
    val uiStateFromSearchPlacesDataResult = _uiStateFromSearchPlacesDataResult.asStateFlow()

    fun requestGifticonDetail(gifticonId: Int) {
        gifticonRepository.requestGifticonDetail(gifticonId)
            .onEach { _gifticonDetailModel.value = it }
            .launchIn(viewModelScope)
    }

    fun searchPlacesByKeywordWithDataResult(
        query: String,
        x: String,
        y: String
    ) {
        viewModelScope.launch {
            kakaoRepository.searchPlacesByKeyword(
                query = query,
                x = x,
                y = y,
                radius = 2000
            ).onStart {
                _searchPlacesDataResult.value = DataResult.Loading
            }.catch { throwable ->
                throwable.stackTrace.forEach {
                    Log.e("MOATest", "[stackTrace] : $it")
                }
                _searchPlacesDataResult.value = DataResult.Failure(throwable = throwable)
            }.collectLatest { searchPlacesModels ->
                _searchPlacesDataResult.value = DataResult.Success(data = searchPlacesModels)
            }
        }
    }

    fun updateNearestScreenUiState(newNearestUseScreenUiState: NearestUseScreenUiState) {
        _nearestUseScreenUiState.value = newNearestUseScreenUiState
    }

    fun updateUiStateFromSearchPlacesDataResult(newUiStateFromSarchPlacesDataResult: UiStateFromSearchPlacesDataResult) {
        _uiStateFromSearchPlacesDataResult.value = newUiStateFromSarchPlacesDataResult
    }
}

@Immutable
sealed class NearestUseScreenUiState {
    object None : NearestUseScreenUiState()
    object Loading : NearestUseScreenUiState()
    object Failure : NearestUseScreenUiState()
}

@Immutable
sealed class UiStateFromSearchPlacesDataResult {
    object None : UiStateFromSearchPlacesDataResult()

    @Immutable
    data class LoadMap(
        val searchPlacesModel: List<SearchPlaceModel>
    ) : UiStateFromSearchPlacesDataResult()
}
