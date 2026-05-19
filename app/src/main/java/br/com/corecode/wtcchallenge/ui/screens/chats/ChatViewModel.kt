package br.com.corecode.wtcchallenge.ui.screens.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.corecode.wtcchallenge.data.repository.ChatRepository
import br.com.corecode.wtcchallenge.data.repository.SessionRepository
import br.com.corecode.wtcchallenge.domain.model.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import br.com.corecode.wtcchallenge.domain.model.Chat

class ChatViewModel(
    private val repository: ChatRepository,
    private val sessionRepository: SessionRepository
): ViewModel() {

    private val _chatList = MutableStateFlow<Result<List<Chat>>>(Result.success(emptyList()))
    val chatList: StateFlow<Result<List<Chat>>> = _chatList.asStateFlow()

    private val _messages = MutableStateFlow<Result<List<Message>>>(Result.success(emptyList()))
    val messages: StateFlow<Result<List<Message>>> = _messages.asStateFlow()

    private var currentChatId: String? = null
    private var userId: String? = null

    init {
        viewModelScope.launch {
            sessionRepository.activeSessionUid.collect { uid ->
                userId = uid
                if(uid != null){
                    observeChats()
                }
            }
        }
    }

    fun observeChats(){
        viewModelScope.launch {
            repository.getChatList(userId.orEmpty())
                .catch { e -> _chatList.value = Result.failure(e) }
                .collect { result -> _chatList.value = result }
        }
    }

    fun selectChat(chatId: String){
        currentChatId = chatId
        viewModelScope.launch {
            repository.getMessages(chatId)
                .catch {e -> _messages.value = Result.failure(e) }
                .collect { result -> _messages.value = result }
        }
    }

    fun sendMessage(text: String, onComplete: (Result<Unit>) -> Unit = {}){
        val chatId = currentChatId ?: return
        val message = Message(
            senderId = userId.orEmpty(),
            text = text,
            timestamp = System.currentTimeMillis()
        )

        viewModelScope.launch {
            val result = repository.sendMessage(chatId, message)
            onComplete(result)
        }
    }
}