package com.example.memorizingwords.state

import com.example.memorizingwords.model.JapaneseWord

data class ModifyWordDataScreenState(
    val word: JapaneseWord = JapaneseWord(createdAt = 0, lastStudiedAt = 0)
)