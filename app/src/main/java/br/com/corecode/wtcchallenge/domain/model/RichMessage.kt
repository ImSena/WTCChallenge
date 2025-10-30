package br.com.corecode.wtcchallenge.domain.model


data class RichMessage(
    val title: String = "",
    val body: String = "",
    val url: String = "",
    val actions: List<RichMessageAction> = emptyList(),
    val actionUrls: Map<String, String> = emptyMap()
) {
    constructor(): this("", "", "", emptyList(), emptyMap())
}

data class RichMessageAction(
    val action: String = "", // "btn1" [cite: 596]
    val title: String = ""   // "Inscrever-se" [cite: 596]
) {
    constructor() : this("", "")
}