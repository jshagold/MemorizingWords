package com.example.memorizingwords.mapper

import com.example.memorizingwords.model.JapaneseWord
import com.example.memorizingwords.model.PartOfSpeechType
import com.example.domain.repository.model.JapaneseWord as JapaneseWordDomain

fun JapaneseWord.toDomain(): JapaneseWordDomain {
    return JapaneseWordDomain(
        id = this.id,
        kanji = this.kanji,
        hiragana = this.hiragana,
        korean = this.korean,
        partOfSpeech = this.partOfSpeech.id,
        exampleSentence = this.exampleSentence,
        isFavorite = this.isFavorite,
        createdAt = this.createdAt,
        lastStudiedAt = this.lastStudiedAt
    )
}

fun JapaneseWordDomain.toUI(): JapaneseWord {
    return JapaneseWord(
        id = this.id,
        kanji = this.kanji,
        hiragana = this.hiragana,
        korean = this.korean,
        partOfSpeech = PartOfSpeechType.findById(this.partOfSpeech),
        exampleSentence = this.exampleSentence,
        isFavorite = this.isFavorite,
        createdAt = this.createdAt,
        lastStudiedAt = this.lastStudiedAt
    )
}