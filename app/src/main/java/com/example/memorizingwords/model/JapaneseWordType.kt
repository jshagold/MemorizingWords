package com.example.memorizingwords.model

import androidx.annotation.StringRes
import com.example.memorizingwords.R

enum class JapaneseWordType(@StringRes val id: Int) {
    KANJI(R.string.japanese_kanji),
    HIRAGANA(R.string.japanese_hiragana),
    KOREAN(R.string.japanese_korean)
}