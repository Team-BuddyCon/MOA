package com.yapp.moa.data.repository.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.yapp.moa.domain.repository.TokenRepository
import com.yapp.moa.local.PreferenceKeys.ACCESS_TOKEN
import com.yapp.moa.local.PreferenceKeys.ACCESS_TOKEN_EXPIRES_IN
import com.yapp.moa.local.PreferenceKeys.IS_FIRST_EXTERNAL_STORAGE
import com.yapp.moa.local.PreferenceKeys.IS_FIRST_INSTALLATION
import com.yapp.moa.local.PreferenceKeys.IS_SHOULD_SHOW_COACH_MARK
import com.yapp.moa.local.PreferenceKeys.IS_TEST_MODE
import com.yapp.moa.local.PreferenceKeys.NICKNAME
import com.yapp.moa.local.PreferenceKeys.REFRESH_TOKEN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : TokenRepository {
    override fun isFirstInstallation(): Flow<Boolean> =
        dataStore.data.map { preference ->
            preference[IS_FIRST_INSTALLATION] ?: true
        }

    override suspend fun checkFirstInstallation() {
        dataStore.edit { preference ->
            preference[IS_FIRST_INSTALLATION] = false
        }
    }

    override fun isFirstExternalStoragePopup(): Flow<Boolean> =
        dataStore.data.map { preference ->
            preference[IS_FIRST_EXTERNAL_STORAGE] ?: true
        }

    override suspend fun checkFirstExternalStoragePopup() {
        dataStore.edit { preference ->
            preference[IS_FIRST_EXTERNAL_STORAGE] = false
        }
    }

    override fun isShouldShowCoachMark(): Flow<Boolean> =
        dataStore.data.map { preference ->
            preference[IS_SHOULD_SHOW_COACH_MARK] ?: true
        }

    override suspend fun stopShowCoachMark() {
        dataStore.edit { preference ->
            preference[IS_SHOULD_SHOW_COACH_MARK] = false
        }
    }

    override fun getNickname(): Flow<String> =
        dataStore.data.map { preference ->
            preference[NICKNAME] ?: ""
        }

    override suspend fun saveNickname(nickname: String) {
        dataStore.edit { preference ->
            preference[NICKNAME] = nickname
        }
    }

    override fun getAccessToken(): Flow<String> =
        dataStore.data.map { preference ->
            preference[ACCESS_TOKEN] ?: ""
        }

    override suspend fun saveAccessToken(accessToken: String) {
        dataStore.edit { preference ->
            preference[ACCESS_TOKEN] = accessToken
        }
    }

    override fun getRefreshToken(): Flow<String> =
        dataStore.data.map { preference ->
            preference[REFRESH_TOKEN] ?: ""
        }

    override suspend fun saveRefreshToken(refreshToken: String) {
        dataStore.edit { preference ->
            preference[REFRESH_TOKEN] = refreshToken
        }
    }

    override fun getAccessTokenExpiresIn(): Flow<Long> =
        dataStore.data.map { preference ->
            preference[ACCESS_TOKEN_EXPIRES_IN] ?: Long.MIN_VALUE
        }

    override suspend fun saveAccessTokenExpiresIn(expire: Long) {
        dataStore.edit { preference ->
            preference[ACCESS_TOKEN_EXPIRES_IN] = expire
        }
    }

    override fun isTestMode(): Flow<Boolean> =
        dataStore.data.map { preference ->
            preference[IS_TEST_MODE] ?: false
        }

    override suspend fun saveTestMode(isTestMode: Boolean) {
        dataStore.edit { preference ->
            preference[IS_TEST_MODE] = isTestMode
        }
    }
}
