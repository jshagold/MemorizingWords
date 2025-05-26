package com.example.memorizingwords.model

enum class PartOfSpeechType(val id: Int, val korean: String, val japanese: String, val english: String) {
    UNKNOWN(0, "모름","分からない","Unknown"),
    NOUN(1, "명사","名詞","Noun"),
    PRONOUN(2, "대명사","代名詞","Pronoun"),
    VERB(3, "동사","動詞","Verb"),
    ADJECTIVE(4, "형용사","形容詞","Adjective"),
    ADVERB(5, "부사","副詞","Adverb"),
    INTERJECTION(6, "감탄사","感動詞","Interjection"),
    CONJUNCTION(7, "접속사","接続詞","Conjunction"),
    PREPOSITION(8, "조사","助詞","Preposition"),
    DETERMINER(9, "관형사","連体詞","Determiner"),
    AUXILIARYVERB(10, "보조동사","補助動詞","Auxiliary verb");

    companion object {
        fun findById(id: Int): PartOfSpeechType {
            return entries.find { it.id == id } ?: UNKNOWN
        }
    }
}

