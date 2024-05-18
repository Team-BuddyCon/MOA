package com.yapp.buddycon.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kakao.vectormap.label.Label
import com.yapp.buddycon.domain.model.gifticon.AvailableGifticon
import com.yapp.buddycon.domain.model.kakao.SearchPlaceModel
import com.yapp.buddycon.domain.model.type.GifticonStore
import com.yapp.buddycon.domain.model.type.SortType
import com.yapp.buddycon.domain.repository.GifticonRepository
import com.yapp.buddycon.domain.repository.KakaoRepository
import com.yapp.buddycon.utility.stability.MapSearchPlace
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val gifticonRepository: GifticonRepository,
    private val kakaoRepository: KakaoRepository
) : ViewModel() {

    private val _mapUiState = MutableStateFlow(MapUiState())
    val mapUiState = _mapUiState.asStateFlow()

    private val _gifticonInfos = MutableStateFlow<PagingData<AvailableGifticon.AvailableGifticonInfo>>(PagingData.empty())
    val gifticonInfos = _gifticonInfos.asStateFlow()

    private val _mapSearchPlace = MutableStateFlow(MapSearchPlace())
    val mapSearchPlace = _mapSearchPlace.asStateFlow()

    // 전체일 경우에 가지고 있는 기프티콘 종류의 매장만 검색
    private val _totalStores = MutableStateFlow<List<GifticonStore>>(listOf())
    val totalStores = _totalStores.asStateFlow()

    private val _placeLabels = MutableStateFlow<Map<SearchPlaceModel, Label?>>(mapOf())
    val placeLabels = _placeLabels.asStateFlow()

    init {
        fetchAvailableGifticon()
        getGifticonCount()
    }

    private fun fetchAvailableGifticon() {
        gifticonRepository.fetchAvailableGifticon(
            gifticonStore = mapUiState.value.store,
            gifticonSortType = SortType.EXPIRATION_DATE
        ).cachedIn(viewModelScope)
            .onEach {
                _gifticonInfos.value = it
            }.launchIn(viewModelScope)
    }

    private fun getGifticonCount() {
        gifticonRepository.getGifticonCount(used = false)
            .onEach {
                _mapUiState.value = _mapUiState.value.copy(
                    totalCount = it
                )
            }.launchIn(viewModelScope)
    }

    fun searchPlacesByKeyword(
        stores: List<GifticonStore>,
        x: String,
        y: String
    ) {
        if (stores.isEmpty()) return
        stores.map { store ->
            kakaoRepository.searchPlacesByKeyword(
                query = store.value,
                x = x,
                y = y,
                radius = 2000
            )
        }.reduce { acc, flow ->
            acc.zip(flow) { searchPlaceModels, searchPlaceModel ->
                searchPlaceModels + searchPlaceModel
            }
        }.onEach {
            _mapSearchPlace.value = _mapSearchPlace.value.copy(searchPlaceModels = it)
        }.launchIn(viewModelScope)
    }

    fun selectGifticonStore(store: GifticonStore) {
        _mapUiState.value = _mapUiState.value.copy(
            store = store
        )
        fetchAvailableGifticon()
    }

    fun changeBottomSheetValue(sheetValue: BottomSheetValue) {
        _mapUiState.value = _mapUiState.value.copy(
            sheetValue = sheetValue,
            heightDp = sheetValue.sheetPeekHeightDp,
            offset = 0f
        )
    }

    fun setOffset(offset: Float) {
        val curHeightDp = mapUiState.value.heightDp
        _mapUiState.value = _mapUiState.value.copy(
            heightDp = curHeightDp - offset,
            offset = -offset
        )
    }

    fun setGifticonItemsInfo(
        deadLineCount: Int,
        totalStores: List<GifticonStore>
    ) {
        _mapUiState.value = _mapUiState.value.copy(
            deadLineCount = deadLineCount
        )
        _totalStores.value = totalStores
    }

    fun setPlaceLabels(placeLabels: Map<SearchPlaceModel, Label?>) {
        _placeLabels.value = placeLabels
    }
}
