package com.yapp.buddycon.startup.login

import androidx.lifecycle.ViewModel
import com.yapp.buddycon.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

}