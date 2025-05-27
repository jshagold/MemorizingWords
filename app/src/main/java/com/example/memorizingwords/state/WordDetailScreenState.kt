package com.example.memorizingwords.state

import com.example.memorizingwords.model.JapaneseWord

data class WordDetailScreenState (
    val word: JapaneseWord = JapaneseWord(
        createdAt = System.currentTimeMillis(),
        lastStudiedAt = System.currentTimeMillis(),
    )
)