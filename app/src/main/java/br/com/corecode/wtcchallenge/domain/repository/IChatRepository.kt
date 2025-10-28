package br.com.corecode.wtcchallenge.domain.repository

import br.com.corecode.wtcchallenge.domain.model.Chat
import br.com.corecode.wtcchallenge.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface IChatRepository {
    fun getChatList(userId: String): Flow<Result<List<Chat>>>
    fun getMessages(chatId: String): Flow<Result<List<Message>>>
    suspend fun sendMessage(chatId: String, message: Message): Result<Unit>
}