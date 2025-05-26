package com.example.data.mapper

import com.example.data.model.Japanese
import com.example.domain.repository.model.JapaneseWord as JapaneseDomain
import com.example.database.model.Japanese as JapaneseEntity


fun Japanese.toEntity(): JapaneseEntity {
    return JapaneseEntity(
        kanji = this.kanji,
        hiragana = this.hiragana,
        korean = this.korean,
        type = this.type,
        exampleSentence = this.exampleSentence,
        isFavorite = this.isFavorite,
        createdAt = this.createdAt,
        lastStudiedAt = this.lastStudiedAt
    )
}

fun Japanese.toDomain(): JapaneseDomain {
    return JapaneseDomain(
        id = this.id,
        kanji = this.kanji,
        hiragana = this.hiragana,
        korean = this.korean,
        type = this.type,
        exampleSentence = this.exampleSentence,
        isFavorite = this.isFavorite,
        createdAt = this.createdAt,
        lastStudiedAt = this.lastStudiedAt
    )
}

fun JapaneseEntity.toData(): Japanese {
    return Japanese(
        id = this.id,
        kanji = this.kanji,
        hiragana = this.hiragana,
        korean = this.korean,
        type = this.type,
        exampleSentence = this.exampleSentence,
        isFavorite = this.isFavorite,
        createdAt = this.createdAt,
        lastStudiedAt = this.lastStudiedAt
    )
}

fun JapaneseDomain.toData(): Japanese {
    return Japanese(
        id = this.id,
        kanji = this.kanji,
        hiragana = this.hiragana,
        korean = this.korean,
        type = this.type,
        exampleSentence = this.exampleSentence,
        isFavorite = this.isFavorite,
        createdAt = this.createdAt,
        lastStudiedAt = this.lastStudiedAt
    )
}
