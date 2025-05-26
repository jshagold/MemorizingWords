package com.example.data.model

data class Japanese(
    val id: Long = 0L,
    val kanji: String,
    val hiragana: String,
    // todo korean id
    val korean: List<String>,
    /** 동사, 형용사, 명사, 등등 구분타입 **/
    val type: String?,
    val exampleSentence: List<String?>,
    val isFavorite: Boolean = false,
    val createdAt: Long,
    val lastStudiedAt: Long,
)
