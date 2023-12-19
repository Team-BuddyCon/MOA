package com.yapp.buddycon.local

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val IS_FIRST_INSTALLATION = booleanPreferencesKey("IS_FIRST_INSTALLATION")
    val NICKNAME = stringPreferencesKey("NICKNAME")
}
