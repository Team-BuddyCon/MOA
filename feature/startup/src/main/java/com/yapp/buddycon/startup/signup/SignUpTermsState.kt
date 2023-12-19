package com.yapp.buddycon.startup.signup

import androidx.compose.runtime.Stable

data class SignUpTermsState(
    val termsOfUse: Boolean = false,
    val privacyPolicy: Boolean = false
) {
    fun isAllChecked() = termsOfUse and privacyPolicy
    fun isEssentialChecked() = termsOfUse and privacyPolicy
}
