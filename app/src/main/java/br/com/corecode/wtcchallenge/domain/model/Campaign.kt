package br.com.corecode.wtcchallenge.domain.model

import com.google.firebase.firestore.DocumentId

data class Campaign(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val body: String = "",
    val mainUrl: String = "",
    val btn1Title: String = "",
    val btn1Url: String = "",

    val btn2Title: String = "",
    val btn2Url: String = "",
    val timestamp: Long = 0L
){
    constructor(): this("", "", "", "", "", "", "", "", 0L)
}