package com.yapp.buddycon.gifticon.edit

import androidx.lifecycle.ViewModel
import com.yapp.buddycon.domain.model.type.GifticonStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class GifticonEditViewModel @Inject constructor(

) : ViewModel() {
    private val _editValueState = MutableStateFlow(GifticonEditUiState())
    val editValueState = _editValueState.asStateFlow()

    fun initEditValueState(name: String, expireDate: String, category: GifticonStore, memo: String) {
        _editValueState.value = _editValueState.value.copy(
            newName = name,
            newExpireDate = expireDate,
            newCategory = category,
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
        _editValueState.value = _editValueState.value.copy(newCategory = category)
    }

    fun setNewMemo(memo: String) {
        _editValueState.value = _editValueState.value.copy(newMemo = memo)
    }
}

data class GifticonEditUiState(
    val newName: String = "",
    val newExpireDate: String = "",
    val newCategory: GifticonStore = GifticonStore.ETC,
    val newMemo: String = ""
)