package br.com.corecode.wtcchallenge.domain.model

import com.google.firebase.firestore.DocumentId

data class Message(
    @DocumentId
    val id: String = "",
    val senderId: String = "",
    val timestamp: Long = 0L,
    val type: String = "text",
    val text: String = "",
    val richMessage: RichMessage? = null
){
    constructor() : this("", "", 0L, "text", "", null)
}
