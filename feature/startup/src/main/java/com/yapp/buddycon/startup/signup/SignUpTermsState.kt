package com.yapp.buddycon.startup.signup

data class SignUpTermsState(
    val termsOfUse: Boolean = false,
    val privacyPolicy: Boolean = false
) {
    fun isAllChecked() = termsOfUse and privacyPolicy
    fun isEssentialChecked() = termsOfUse and privacyPolicy
}
