package br.com.corecode.wtcchallenge.domain.model

data class Campaign(
    val id: String,
    val title: String,
    val body: String,
    val actions: List<String>
)