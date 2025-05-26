package com.example.memorizingwords.state

import com.example.memorizingwords.model.JapaneseWord
import com.example.memorizingwords.model.JapaneseWordSortedType

data class WordListScreenState(
    val sortType: JapaneseWordSortedType = JapaneseWordSortedType.HIRAGANA,
)
