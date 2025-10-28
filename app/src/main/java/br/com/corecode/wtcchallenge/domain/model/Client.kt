package br.com.corecode.wtcchallenge.domain.model

import com.google.firebase.firestore.DocumentId

data class Client (
    @DocumentId
    val id: String = "",
    val name: String = "",
    val company: String = "",
    val email: String = "",
    val score: Int = 0,
    val status: String = "",
    val tags: List<String> = emptyList()
)