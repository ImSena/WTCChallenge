package br.com.corecode.wtcchallenge.ui.screens.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.corecode.wtcchallenge.data.repository.SessionRepository
import br.com.corecode.wtcchallenge.data.repository.UserRepository
import br.com.corecode.wtcchallenge.domain.usecase.LogoutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application): AndroidViewModel(application) {
    private val sessionRepository = SessionRepository(application)
    private val userRepository = UserRepository(sessionRepository)
    private val logoutUseCase = LogoutUseCase(userRepository)

    private val _isLoggingOut = MutableStateFlow(false)
    val isLoggingOut = _isLoggingOut.asStateFlow()

    fun logout(onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            _isLoggingOut.value = true
            val result = logoutUseCase()
            _isLoggingOut.value = false
            onComplete(result.isSuccess)
        }
    }
}