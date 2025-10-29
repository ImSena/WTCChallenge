package br.com.corecode.wtcchallenge.ui.screens.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.corecode.wtcchallenge.data.repository.SessionRepository

class ChatViewModelFactory(
    private val sessionRepository: SessionRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel(sessionRepository = sessionRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
