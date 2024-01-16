package com.yapp.buddycon.gifticon.available

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.repository.AvailableGifticonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AvailableGifticonViewModel @Inject constructor(
    private val availableGifticonRepository: AvailableGifticonRepository
) : ViewModel() {

    fun getAvailableGifiticon() {
        viewModelScope.launch {
            availableGifticonRepository.getAvailableGifiticon()
                .onStart {
                    // set loading state
                    Log.e("BuddyConTest", "loading...")
                }.catch {
                    // error handling
                    Log.e("BuddyConTest", "catch error!")
                }.collectLatest { availableGifticon ->
                    Log.e("BuddyConTest", "collect data : $availableGifticon")
                }
        }
    }
}