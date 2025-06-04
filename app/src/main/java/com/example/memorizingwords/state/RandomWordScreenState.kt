package com.example.memorizingwords.state

import com.example.memorizingwords.model.JapaneseWord
import com.example.memorizingwords.model.JapaneseWordType

data class RandomWordScreenState(
    val index: Int = 0,
    val word: JapaneseWord = JapaneseWord(createdAt = System.currentTimeMillis(), lastStudiedAt = System.currentTimeMillis()),
    val type: JapaneseWordType = JapaneseWordType.KANJI
)