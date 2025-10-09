package br.com.corecode.wtcchallenge.domain.repository.interfaces

import br.com.corecode.wtcchallenge.data.model.User

interface IUserRepository {
    suspend fun login(email: String, password: String): Result<User>
}