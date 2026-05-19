package br.com.corecode.wtcchallenge.data.network

import br.com.corecode.wtcchallenge.domain.model.Chat
import br.com.corecode.wtcchallenge.domain.model.Message
import retrofit2.Response
import retrofit2.http.*

interface ChatApiService {

    @GET("/inbox/{userId}")
    suspend fun getUserInbox(@Path("userId") userId: String): Response<List<Chat>>

    @GET("/messages/{chatId}")
    suspend fun getChatMessages(@Path("chatId") chatId: String): Response<List<Message>>

    @POST("/messages")
    suspend fun sendMessage(
        @Query("chatId") chatId: String,
        @Body message: Message
    ): Response<Message>
}