package br.com.corecode.wtcchallenge.ui.screens.chats

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.corecode.wtcchallenge.data.repository.ChatRepository
import br.com.corecode.wtcchallenge.data.repository.SessionRepository

class ChatViewModelFactory(
    private val context: Context,
    private val sessionRepository: SessionRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            val repository = ChatRepository(context)
            return ChatViewModel(repository = repository, sessionRepository = sessionRepository) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }
}