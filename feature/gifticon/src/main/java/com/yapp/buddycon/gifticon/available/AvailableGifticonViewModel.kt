package com.yapp.buddycon.gifticon.available

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.model.gifticon.AvailableGifticon
import com.yapp.buddycon.domain.model.type.GifticonStoreCategory
import com.yapp.buddycon.domain.model.type.SortType
import com.yapp.buddycon.domain.repository.AvailableGifticonRepository
import com.yapp.buddycon.domain.result.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class AvailableGifticonViewModel @Inject constructor(
    private val availableGifticonRepository: AvailableGifticonRepository
) : ViewModel() {
    private var _availabeGifticonDataResult = MutableStateFlow<DataResult<AvailableGifticon>>(DataResult.None)
    val availableGifticonDataResult = _availabeGifticonDataResult.asStateFlow()

    private var _currentAvailableGifiticons = MutableStateFlow(arrayListOf<AvailableGifticon.AvailableGifticonInfo>())
    val currentAvailableGifticons = _currentAvailableGifiticons.asStateFlow()

    private var _availableGifticonDetailState = MutableStateFlow(AvailableGifticonDetailState())
    val availableGifticonDetailState = _availableGifticonDetailState.asStateFlow()

    private var _availableGifticonScreenUiState = MutableStateFlow<AvailableGifticonScreenUiState>(AvailableGifticonScreenUiState.None)
    val availableGifticonScreenUiState = _availableGifticonScreenUiState.asStateFlow()

    private var availableGifticonPageState = AvailableGifticonPageState()

    fun getAvailableGifiticon() {
        if (availableGifticonPageState.isCurrentTabLastPage.not()) {
            viewModelScope.launch {
                availableGifticonPageState = availableGifticonPageState.copy(currentPage = availableGifticonPageState.currentPage + 1)

                availableGifticonRepository.getAvailableGifiticon(
                    gifticonStoreCategory = _availableGifticonDetailState.value.currentStoreCategory,
                    gifticonSortType = availableGifticonDetailState.value.currentSortType,
                    currentPage = availableGifticonPageState.currentPage
                ).onStart {
                    // set loading state
                    Log.e("MOATest", "loading...")
                    _availabeGifticonDataResult.value = DataResult.Loading
                }.catch { throwable ->
                    // error handling
                    Log.e("MOATest", "catch error!}")
                    throwable.stackTrace.forEach {
                        Log.e("MOATest", "[stackTrace] : $it")
                    }
                    _availabeGifticonDataResult.value = DataResult.Failure(throwable = throwable)
                }.collectLatest { availableGifticon ->
                    Log.e("MOATest", "collect data : $availableGifticon")
                    _availabeGifticonDataResult.value = DataResult.Success(data = availableGifticon)
                }
            }
        }
    }

    fun updateCurrentStoreCategoryTab(newStoreCategory: GifticonStoreCategory) {
        if (_availableGifticonDetailState.value.currentStoreCategory != newStoreCategory) {
            _availableGifticonDetailState.value = _availableGifticonDetailState.value.copy(currentStoreCategory = newStoreCategory)
            availableGifticonPageState = availableGifticonPageState.copy(currentPage = -1, isCurrentTabLastPage = false)

            getAvailableGifiticon()
        }
    }

    fun updateCurrentSortType(newSortType: SortType) {
        if (_availableGifticonDetailState.value.currentSortType != newSortType) {
            _availableGifticonDetailState.value = _availableGifticonDetailState.value.copy(currentSortType = newSortType)
            availableGifticonPageState = availableGifticonPageState.copy(currentPage = -1, isCurrentTabLastPage = false)

            getAvailableGifiticon()
        }
    }

    fun updateCurrentAvailabeGifticons(addedAvailableGifticon: AvailableGifticon) {
        val tempList = arrayListOf<AvailableGifticon.AvailableGifticonInfo>().apply {
            if (addedAvailableGifticon.isFirstPage.not()) { // 첫 페이지가 아닌 경우
                addAll(_currentAvailableGifiticons.value)
            }
            addAll(addedAvailableGifticon.availableGifticons)
        }

        _currentAvailableGifiticons.value = tempList
        availableGifticonPageState = availableGifticonPageState.copy(isCurrentTabLastPage = addedAvailableGifticon.isLastPage)
    }

    fun updateAvailableScreenUiState(newAvailableGifticonScreenUiState: AvailableGifticonScreenUiState) {
        _availableGifticonScreenUiState.value = newAvailableGifticonScreenUiState
    }
}

@Immutable
data class AvailableGifticonDetailState(
    val currentStoreCategory: GifticonStoreCategory = GifticonStoreCategory.TOTAL,
    val currentSortType: SortType = SortType.EXPIRATION_DATE,
)

data class AvailableGifticonPageState(
    val currentPage: Int = -1,
    val isCurrentTabLastPage: Boolean = false
)

@Immutable
sealed class AvailableGifticonScreenUiState {
    object None : AvailableGifticonScreenUiState()
    object Loading : AvailableGifticonScreenUiState()
    object Failure : AvailableGifticonScreenUiState()
}
