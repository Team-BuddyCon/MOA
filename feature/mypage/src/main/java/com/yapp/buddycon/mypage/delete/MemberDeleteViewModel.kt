package com.yapp.buddycon.mypage.delete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

enum class DeleteMemberPhrase {
    FIRST, SECOND, THIRD
}

enum class DeleteMemberReason(val reason: String) {
    NOT_USED("쓰지 않는 앱이에요"),
    HAVE_ISSUES("오류가 생겨서 쓸 수 없어요"),
    DONT_KNOW_HOW_TO_USE("앱 사용법을 모르겠어요"),
    ETC("기타")
}

@HiltViewModel
class MemberDeleteViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
) : ViewModel() {
    private val _phrase = MutableStateFlow(DeleteMemberPhrase.FIRST)
    val phrase = _phrase.asStateFlow()

    private val _reason = MutableStateFlow(DeleteMemberReason.NOT_USED)
    val reason = _reason.asStateFlow()

    private val _reasonText = MutableStateFlow("")
    val reasonText = _reasonText.asStateFlow()

    private val _userName = MutableStateFlow("")
    val userName = _userName.asStateFlow()

    init {
        getUserName()
    }

    private fun getUserName() {
        tokenRepository.getNickname()
            .onEach { _userName.value = it }
            .launchIn(viewModelScope)
    }

    fun setPhrase(phrase: DeleteMemberPhrase) {
        _phrase.value = phrase
    }

    fun setReason(reason: DeleteMemberReason) {
        _reason.value = reason
    }

    fun setReasonText(text: String) {
        _reasonText.value = text
    }
}
