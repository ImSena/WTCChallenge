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
        val JWT_TOKEN = stringPreferencesKey("jwt_token")
    }

    override suspend fun saveSession(uid: String, role: String, token: String) {
        dataStore.edit{preferences ->
            preferences[PreferencesKeys.USER_UID] = uid
            preferences[PreferencesKeys.USER_ROLE] = role
            preferences[PreferencesKeys.JWT_TOKEN] = token
        }
    }

    override suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.USER_UID)
            preferences.remove(PreferencesKeys.USER_ROLE)
            preferences.remove(PreferencesKeys.JWT_TOKEN)
        }
    }

    override val activeSessionUid: Flow<String?> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.USER_UID]
    }

    override val activeUserRole: Flow<String?> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.USER_ROLE]
    }

    val activeJwtToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.JWT_TOKEN]
    }

}