package com.yapp.buddycon.local

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val IS_FIRST_INSTALLATION = booleanPreferencesKey("IS_FIRST_INSTALLATION")
    val IS_FIRST_EXTERNAL_STORAGE = booleanPreferencesKey("IS_FIRST_EXTERNAL_STORAGE")
    val IS_SHOULD_SHOW_COACH_MARK = booleanPreferencesKey("IS_SHOULD_SHOW_COACH_MARK")
    val NICKNAME = stringPreferencesKey("NICKNAME")
    val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
    val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")
    val ACCESS_TOKEN_EXPIRES_IN = longPreferencesKey("ACCESS_TOKEN_EXPIRES_IN")
    val IS_TEST_MODE = booleanPreferencesKey("IS_TEST_MODE")
}
