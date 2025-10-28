package br.com.corecode.wtcchallenge.domain.model

import com.google.firebase.firestore.DocumentId

data class Chat(
    @DocumentId
    val id: String = "",

    val participants: List<String> = emptyList(),

    val lastMessage: String = "",
    val lastMessageTimestamp: Long = 0,
    val participantsDetails: Map<String, ParticipantInfo> = emptyMap()
)

data class ParticipantInfo(
    val name: String = "",
    val avatarUrl: String = ""
)
