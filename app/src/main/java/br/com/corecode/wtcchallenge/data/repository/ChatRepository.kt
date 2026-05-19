package br.com.corecode.wtcchallenge.data.repository

import android.content.Context
import android.util.Log
import br.com.corecode.wtcchallenge.data.network.RetrofitClient
import br.com.corecode.wtcchallenge.domain.model.Chat
import br.com.corecode.wtcchallenge.domain.model.Message
import br.com.corecode.wtcchallenge.domain.repository.IChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class ChatRepository(
    context: Context,
    private val sessionRepository: SessionRepository = SessionRepository(context)
) : IChatRepository {

    private val chatService = RetrofitClient.getChatService {
        runBlocking(Dispatchers.IO) {
            try {
                sessionRepository.activeJwtToken.first()
            } catch (e: Exception) {
                null
            }
        }
    }
    private val TAG = "ChatRepository"

    override fun getChatList(userId: String): Flow<Result<List<Chat>>> = flow {
        while (true) {
            try {
                val response = chatService.getUserInbox(userId)
                if (response.isSuccessful && response.body() != null) {
                    emit(Result.success(response.body()!!))
                } else {
                    emit(Result.failure(Exception("Erro ao buscar caixa de entrada: ${response.code()}")))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Falha na conexão de rede do Inbox", e)
                emit(Result.failure(e))
            }
            delay(4000)
        }
    }.flowOn(Dispatchers.IO)

    override fun getMessages(chatId: String): Flow<Result<List<Message>>> = flow {
        while (true) {
            try {
                val response = chatService.getChatMessages(chatId)
                if (response.isSuccessful && response.body() != null) {
                    emit(Result.success(response.body()!!))
                } else {
                    emit(Result.failure(Exception("Erro ao buscar mensagens: ${response.code()}")))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Falha na conexão de rede do Chat", e)
                emit(Result.failure(e))
            }
            delay(2000)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun sendMessage(chatId: String, message: Message): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = chatService.sendMessage(chatId, message)
                if (response.isSuccessful) {
                    Log.d(TAG, "Mensagem persistida no MongoDB e Push disparado pelo Spring!")
                    Result.success(Unit)
                } else {
                    Log.e(TAG, "Erro retornado pela API Spring: ${response.code()}")
                    Result.failure(Exception("Falha ao enviar mensagem via HTTP API."))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exceção de rede ao enviar mensagem", e)
                Result.failure(e)
            }
        }
    }
}