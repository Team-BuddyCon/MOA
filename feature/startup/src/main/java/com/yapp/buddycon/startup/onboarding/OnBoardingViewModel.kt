package com.yapp.buddycon.startup.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor() : ViewModel() {
    private val _isTestMode = MutableStateFlow(false)
    val isTestMode = _isTestMode.asStateFlow()

    init {
        fetchRemoteConfig()
    }

    // 처음 Splash에서 Fetch 시 시간이 오래걸려 늦게 값을 늦게 받아와서 OnBoarding에서도 요청
    private fun fetchRemoteConfig() {
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val mode = remoteConfig.getBoolean("isTestMode")
                    viewModelScope.launch {
                        _isTestMode.value = mode
                    }
                    Timber.d("Firebase Remote Config fetch and Active success isTestMode: $mode")
                } else {
                    Timber.d("Firebase Remote Config fetch and Active fail")
                }
            }
    }
}
