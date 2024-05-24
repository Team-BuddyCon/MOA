package com.yapp.buddycon.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.user.UserApiClient
import com.yapp.buddycon.domain.repository.GifticonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val gifticonRepository: GifticonRepository
) : ViewModel() {
    private var _userName = MutableStateFlow("")
    val userName = _userName.asStateFlow()

    private var _usedGifticonCount = MutableStateFlow(0)
    val usedGifticonCount = _usedGifticonCount.asStateFlow()

    init {
        loadUserName()
        getUsedGifticonCount()
    }

    fun loadUserName() {
        viewModelScope.launch {
            UserApiClient.instance.me { user, error ->
                if (error != null) {
                    Log.d("MOATest", "사용자 정보 요청 실패 : $error")
                } else if (user != null) {
                    Log.d(
                        "MOATest",
                        "사용자 정보 요청 성공" +
                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                            "\n이름: ${user.kakaoAccount?.name}"
                    )
                    user.kakaoAccount?.profile?.nickname?.let { nickName ->
                        _userName.update { nickName }
                    }
                } else {
                    Log.d("MOATest", "else")
                }
            }
        }
    }

    fun getUsedGifticonCount() {
        gifticonRepository.getGifticonCount(used = true)
            .onEach {
                _usedGifticonCount.value = it
            }.launchIn(viewModelScope)
    }
}
