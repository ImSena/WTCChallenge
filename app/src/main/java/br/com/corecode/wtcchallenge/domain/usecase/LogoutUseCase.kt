package br.com.corecode.wtcchallenge.domain.usecase

import br.com.corecode.wtcchallenge.data.repository.UserRepository

class LogoutUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(): Result<Unit> = userRepository.logout()
}