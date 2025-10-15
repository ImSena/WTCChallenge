package br.com.corecode.wtcchallenge.domain.repository

import br.com.corecode.wtcchallenge.data.model.User

interface IUserRepository {
    suspend fun login(email: String, password: String): Result<User>

    suspend fun logout() : Result<Unit>
}