package br.com.corecode.wtcchallenge.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import br.com.corecode.wtcchallenge.domain.repository.ISessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class SessionRepository(context: Context) : ISessionRepository {
    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val USER_UID = stringPreferencesKey("user_uid")
        val USER_ROLE = stringPreferencesKey("user_role")
    }

    override suspend fun saveSession(uid: String, role: String) {
        dataStore.edit{preferences ->
            preferences[PreferencesKeys.USER_UID] = uid
            preferences[PreferencesKeys.USER_ROLE] = role
        }
    }

    override suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.USER_UID)
            preferences.remove(PreferencesKeys.USER_ROLE)
        }
    }

    override val activeSessionUid: Flow<String?> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.USER_UID]
    }

    override val activeUserRole: Flow<String?> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.USER_ROLE]
    }

}