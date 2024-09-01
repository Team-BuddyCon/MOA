package com.yapp.moa.mypage.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.moa.domain.model.notification.NotificationModel
import com.yapp.moa.domain.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MyPageNotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : ViewModel() {
    private val _notiSettings = MutableStateFlow(NotificationModel())
    val notiSettings = _notiSettings.asStateFlow()

    init {
        getNotificationSettings()
    }

    private fun getNotificationSettings() {
        notificationRepository.getNotificationSettings()
            .onEach { _notiSettings.value = it }
            .launchIn(viewModelScope)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun updateNotificationSettings(
        activated: Boolean = notiSettings.value.activated,
        fourteenDaysBefore: Boolean = notiSettings.value.fourteenDaysBefore,
        sevenDaysBefore: Boolean = notiSettings.value.sevenDaysBefore,
        threeDaysBefore: Boolean = notiSettings.value.threeDaysBefore,
        oneDayBefore: Boolean = notiSettings.value.oneDayBefore,
        theDay: Boolean = notiSettings.value.theDay
    ) {
        // 빠른 UI 변경 처리
        _notiSettings.value = NotificationModel(
            activated = activated,
            fourteenDaysBefore = if (activated) fourteenDaysBefore else false,
            sevenDaysBefore = if (activated) sevenDaysBefore else false,
            threeDaysBefore = if (activated) threeDaysBefore else false,
            oneDayBefore = if (activated) oneDayBefore else false,
            theDay = if (activated) theDay else false
        )
        notificationRepository.updateNotificationSettings(
            activated = activated,
            fourteenDaysBefore = if (activated) fourteenDaysBefore else false,
            sevenDaysBefore = if (activated) sevenDaysBefore else false,
            threeDaysBefore = if (activated) threeDaysBefore else false,
            oneDayBefore = if (activated) oneDayBefore else false,
            theDay = if (activated) theDay else false
        ).flatMapLatest {
            notificationRepository.getNotificationSettings()
        }.onEach {
            _notiSettings.value = it
        }.launchIn(viewModelScope)
    }
}
