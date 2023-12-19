package com.yapp.buddycon.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.yapp.buddycon.domain.repository.TokenRepository
import com.yapp.buddycon.local.PreferenceKeys.IS_FIRST_INSTALLATION
import com.yapp.buddycon.local.PreferenceKeys.NICKNAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : TokenRepository {
    override fun isFirstInstallation(): Flow<Boolean> =
        dataStore.data.map { preference ->
            preference[IS_FIRST_INSTALLATION] ?: false
        }

    override suspend fun checkFirstInstallation() {
        dataStore.edit { preference ->
            preference[IS_FIRST_INSTALLATION] = true
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
}
