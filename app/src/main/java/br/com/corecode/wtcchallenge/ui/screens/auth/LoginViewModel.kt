package br.com.corecode.wtcchallenge.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.corecode.wtcchallenge.domain.repository.UserRepository
import br.com.corecode.wtcchallenge.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false,
    val error: String? = null,
    val email: String = "",
    val password: String = ""
)

sealed class LoginEvent{
    data class EmailChanged(val value: String): LoginEvent()
    data class PasswordChanged(val value: String): LoginEvent()
    object LoginClicked : LoginEvent()
}

class LoginViewModel : ViewModel(){

    private val userRepository = UserRepository()
    private val LoginUseCase = LoginUseCase(userRepository)

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: LoginEvent){
        when (event){
            is LoginEvent.EmailChanged -> {
                _uiState.update { it.copy(email = event.value) }
            }
            is LoginEvent.PasswordChanged -> {
                _uiState.update { it.copy(password = event.value) }
            }
            is LoginEvent.LoginClicked -> {
                login(_uiState.value.email, _uiState.value.password)
            }
        }
    }

    private fun login(email: String, password: String){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            if(email.isBlank() || password.isBlank()){
                _uiState.update { it.copy(isLoading = false, error = "E-mail e senha não podem estar em branco.") }
                return@launch
            }

            val result = LoginUseCase(email, password)

            result.onSuccess {
                _uiState.update { it.copy(isLoading = false, loginSuccess = true) }
            }.onFailure { exception ->
                _uiState.update { it.copy(isLoading = false, error = exception.message) }
            }
        }
    }

}