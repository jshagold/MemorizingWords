package com.example.memorizingwords.state

import com.example.memorizingwords.model.JapaneseWordType

data class WordListScreenState(
    val keyword: String = "",
    val searchType: JapaneseWordType = JapaneseWordType.KANJI,
    val sortType: JapaneseWordType = JapaneseWordType.HIRAGANA,
)
