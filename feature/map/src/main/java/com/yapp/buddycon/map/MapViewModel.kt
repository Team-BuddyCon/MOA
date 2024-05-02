package com.yapp.buddycon.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yapp.buddycon.domain.model.gifticon.AvailableGifticon
import com.yapp.buddycon.domain.model.type.GifticonStore
import com.yapp.buddycon.domain.model.type.SortType
import com.yapp.buddycon.domain.repository.GifticonRepository
import com.yapp.buddycon.domain.repository.KakaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

enum class BottomSheetValue(val sheetPeekHeightDp: Float) {
    Collapsed(36f),
    PartiallyExpanded(111f),
    Expanded(540f)
}

@HiltViewModel
class MapViewModel @Inject constructor(
    private val gifticonRepository: GifticonRepository,
    private val kakaoRepository: KakaoRepository
) : ViewModel() {

    private var _store = MutableStateFlow(GifticonStore.TOTAL)
    val store = _store.asStateFlow()

    private var _sheetValue = MutableStateFlow(BottomSheetValue.Collapsed)
    val sheetValue = _sheetValue.asStateFlow()

    private var _heightDp = MutableStateFlow(BottomSheetValue.Collapsed.sheetPeekHeightDp)
    val heightDp = _heightDp.asStateFlow()

    private var _offset = MutableStateFlow(0f)
    val offset = _offset.asStateFlow()

    private val _gifticonPagingItems = MutableStateFlow<PagingData<AvailableGifticon.AvailableGifticonInfo>>(PagingData.empty())
    val gifticonPagingItems = _gifticonPagingItems.asStateFlow()

    private val _deadLineCount = MutableStateFlow(0)
    val deadLineCount = _deadLineCount.asStateFlow()

    private val _totalCount = MutableStateFlow(0)
    val totalCount = _totalCount.asStateFlow()

    private val _mapSearchPlace = MutableStateFlow(MapSearchPlace())
    val mapSearchPlace = _mapSearchPlace.asStateFlow()

    // 전체일 경우에 가지고 있는 기프티콘 종류의 매장만 검색
    private val _gifticonExistStore = MutableStateFlow<List<GifticonStore>>(listOf())
    val gifticonExistStore = _gifticonExistStore.asStateFlow()

    init {
        fetchAvailableGifticon()
        getGifticonCount()
    }

    private fun fetchAvailableGifticon() {
        gifticonRepository.fetchAvailableGifticon(
            gifticonStore = store.value,
            gifticonSortType = SortType.EXPIRATION_DATE
        ).cachedIn(viewModelScope)
            .onEach {
                _gifticonPagingItems.value = it
            }.launchIn(viewModelScope)
    }

    private fun getGifticonCount() {
        gifticonRepository.getGifticonCount(used = false)
            .onEach {
                _totalCount.value = it
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
        _store.value = store
        fetchAvailableGifticon()
    }

    fun changeBottomSheetValue(sheetValue: BottomSheetValue) {
        _sheetValue.value = sheetValue
        _heightDp.value = sheetValue.sheetPeekHeightDp
        _offset.value = 0f
    }

    fun setOffset(offset: Float) {
        _heightDp.value -= offset
        _offset.value = -offset
    }

    fun setGifticonItemsInfo(
        deadLineCount: Int,
        gifticonStores: List<GifticonStore>
    ) {
        _deadLineCount.value = deadLineCount
        _gifticonExistStore.value = gifticonStores
    }
}
