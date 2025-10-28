package br.com.corecode.wtcchallenge.data.repository

import android.util.Log
import br.com.corecode.wtcchallenge.domain.model.Chat
import br.com.corecode.wtcchallenge.domain.model.Message
import br.com.corecode.wtcchallenge.domain.repository.IChatRepository
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class ChatRepository : IChatRepository {

    private val db = Firebase.firestore
    private val chatsCollection = db.collection("chats")
    private val TAG = "ChatRepository"

    override fun getChatList(userId: String): Flow<Result<List<Chat>>> {
        return chatsCollection
            .whereArrayContains("participants", userId)
            .orderBy("lastMessageTimestamp", Query.Direction.DESCENDING)
            .snapshots()
            .map { snapshot ->
                Log.d(TAG, "Lista de chats atualizada")
                Result.success(snapshot.toObjects<Chat>())
            }
            .catch { e ->
                Log.e(TAG, "Erro ao ouvir lista de chats", e)
                emit(Result.failure(e))
            }
    }

    override fun getMessages(chatId: String): Flow<Result<List<Message>>> {
        return chatsCollection.document(chatId)
            .collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .limitToLast(50)
            .snapshots()
            .map { snapshot ->
                Log.d(TAG, "Novas mensagens no chat $chatId")
                Result.success(snapshot.toObjects<Message>())
            }
            .catch { e ->
                Log.e(TAG, "Erro ao ouvir mensagens do chat $chatId", e)
                emit(Result.failure(e))
            }
    }

    override suspend fun sendMessage(chatId: String, message: Message): Result<Unit> {
        return try {
            val chatDocRef = chatsCollection.document(chatId)
            val messagesColRef = chatDocRef.collection("messages")
            messagesColRef.add(message).await()

            val chatUpdates = mapOf(
                "lastMessage" to message.text,
                "lastMessageTimestamp" to message.timestamp
            )
            chatDocRef.update(chatUpdates).await()

            Log.d(TAG, "Mensagem enviada no chat $chatId")

            // TODO: Aqui é onde um Cloud Function entraria em ação
            // para ler essa nova mensagem e disparar o Push (FCM)
            // para os outros participantes do chat.

            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao enviar mensagem", e)
            Result.failure(e)
        }
    }
}