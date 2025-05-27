package com.example.memorizingwords.model

data class JapaneseWord(
    val id: Long = 0L,
    val kanji: String? = null,
    val hiragana: String = "",
    // todo korean id
    val korean: List<String?> = listOf(),
    /** 동사, 형용사, 명사, 등등 구분타입 **/ // todo enum
    val partOfSpeech: PartOfSpeechType = PartOfSpeechType.UNKNOWN,
    val exampleSentence: List<String?> = listOf(),
    val isFavorite: Boolean = false,
    val createdAt: Long,
    val lastStudiedAt: Long,
)
