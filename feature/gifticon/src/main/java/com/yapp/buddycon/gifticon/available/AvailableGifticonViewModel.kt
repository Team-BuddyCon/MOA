package com.yapp.buddycon.gifticon.available

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.model.gifticon.AvailableGifticon
import com.yapp.buddycon.domain.model.type.GifticonStoreCategory
import com.yapp.buddycon.domain.repository.AvailableGifticonRepository
import com.yapp.buddycon.domain.result.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AvailableGifticonViewModel @Inject constructor(
    private val availableGifticonRepository: AvailableGifticonRepository
) : ViewModel() {
    private var _currentStoreCategoryTab = MutableStateFlow(GifticonStoreCategory.TOTAL)
    val currentStoreCategoryTab = _currentStoreCategoryTab.asStateFlow()

    private var _availabeGifticonDataResult = MutableStateFlow<DataResult<AvailableGifticon>>(DataResult.None)
    val availableGifticonDataResult = _availabeGifticonDataResult.asStateFlow()

    private var _currentAvailableGifiticons = MutableStateFlow(arrayListOf<AvailableGifticon.AvailableGifticonInfo>())
    val currentAvailableGifticons = _currentAvailableGifiticons.asStateFlow()

    private var currentTabPage = -1
    private var isCurrentTabLastPage = false

    fun getAvailableGifiticon() {
        if (isCurrentTabLastPage.not()) {
            viewModelScope.launch {
                availableGifticonRepository.getAvailableGifiticon(
                    gifticonStoreCategory = _currentStoreCategoryTab.value,
                    currentPage = ++currentTabPage
                ).onStart {
                    // set loading state
                    Log.e("BuddyConTest", "loading...")
                    _availabeGifticonDataResult.value = DataResult.Loading
                }.catch { throwable ->
                    // error handling
                    Log.e("BuddyConTest", "catch error!")
                    _availabeGifticonDataResult.value = DataResult.Failure(throwable = throwable)
                }.collectLatest { availableGifticon ->
                    Log.e("BuddyConTest", "collect data : $availableGifticon")
                    _availabeGifticonDataResult.value = DataResult.Success(data = availableGifticon)
                }
            }
        }
    }

    fun updateCurrentStoreCategoryTab(newStoreCategory: GifticonStoreCategory) {
        if (_currentStoreCategoryTab.value != newStoreCategory) {
            _currentStoreCategoryTab.value = newStoreCategory // 새로운 탭 상태
            currentTabPage = -1 // page 초기화
            isCurrentTabLastPage = false // 마지막 page 여부 초기화
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
        isCurrentTabLastPage = addedAvailableGifticon.isLastPage
    }
}
