package com.yapp.buddycon.gifticon.edit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.buddycon.domain.model.type.GifticonStore
import com.yapp.buddycon.domain.repository.GifticonRepository
import com.yapp.buddycon.domain.result.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class GifticonEditViewModel @Inject constructor(
    private val gifticonRepository: GifticonRepository
) : ViewModel() {
    private val _editValueState = MutableStateFlow(GifticonEditUiState())
    val editValueState = _editValueState.asStateFlow()

    private var _editGifticonDetailDataResult = MutableStateFlow<DataResult<Unit>>(DataResult.None)
    val editGifticonDetailDataResult = _editGifticonDetailDataResult.asStateFlow()

    fun initEditValueState(name: String, expireDate: String, category: GifticonStore, memo: String) {
        _editValueState.value = _editValueState.value.copy(
            newName = name,
            newExpireDate = expireDate,
            newGifticonStore = category,
            newMemo = memo
        )
    }

    fun setNewName(name: String) {
        _editValueState.value = _editValueState.value.copy(newName = name)
    }

    fun setNewExpirationDate(expireDate: Long) {
        _editValueState.value = _editValueState.value.copy(
            newExpireDate = if (expireDate == 0L) {
                ""
            } else {
                SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(Date(expireDate))
            }
        )
    }

    fun setNewCategory(category: GifticonStore) {
        _editValueState.value = _editValueState.value.copy(newGifticonStore = category)
    }

    fun setNewMemo(memo: String) {
        _editValueState.value = _editValueState.value.copy(newMemo = memo)
    }

    fun editGifticonDetail(gifticonId: Int) {
        viewModelScope.launch {
            with(_editValueState.value) {
                gifticonRepository.editGifticonDetail(
                    gifticonId = gifticonId,
                    name = newName,
                    expireDate = newExpireDate,
                    gifticonStore = newGifticonStore.name,
                    memo = newMemo
                ).onStart {
                    _editGifticonDetailDataResult.value = DataResult.Loading
                }.catch { throwable ->
                    throwable.stackTrace.forEach {
                        Log.e("MOATest", "[stackTrace] : $it")
                    }
                    _editGifticonDetailDataResult.value = DataResult.Failure(throwable = throwable)
                }.collectLatest {
                    _editGifticonDetailDataResult.value = DataResult.Success(data = Unit)
                }
            }
        }
    }
}

data class GifticonEditUiState(
    val newName: String = "",
    val newExpireDate: String = "",
    val newGifticonStore: GifticonStore = GifticonStore.OTHERS,
    val newMemo: String = ""
)
