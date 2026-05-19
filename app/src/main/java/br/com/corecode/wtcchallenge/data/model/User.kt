package br.com.corecode.wtcchallenge.data.model

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val id: String = "",
    val email: String? = "",
    val name: String = "",
    val role: String = "cliente"
)
