package br.com.corecode.wtcchallenge.domain.model

import com.google.firebase.firestore.DocumentId

data class Chat(
    @DocumentId
    val id: String = "",

    val participantsChat: List<String> = emptyList(),

    val lastMessage: String = "",
    val lastMessageTimestamp: Long = 0,
    val participantsDetails: Map<String, ParticipantInfo> = emptyMap(),
    val isBroadcast: Boolean = false
)

data class ParticipantInfo(
    val name: String = "",
    val avatarUrl: String = ""
)
