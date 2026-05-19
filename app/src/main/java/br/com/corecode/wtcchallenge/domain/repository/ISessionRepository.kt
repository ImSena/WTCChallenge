package br.com.corecode.wtcchallenge.domain.repository

import kotlinx.coroutines.flow.Flow


interface ISessionRepository {
    suspend fun saveSession(uid: String, role: String, token: String)
    suspend fun clearSession()
    val activeSessionUid: Flow<String?>
    val activeUserRole: Flow<String?>
}