package br.com.corecode.wtcchallenge.domain.model

data class ClientNote(
    val text: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
