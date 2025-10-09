package br.com.corecode.wtcchallenge.domain.usecase

import br.com.corecode.wtcchallenge.domain.repository.UserRepository

class LoginUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(email: String, password: String) = userRepository.login(email, password)
}