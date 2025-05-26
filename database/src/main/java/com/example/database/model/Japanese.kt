package com.example.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "japanese"
)
data class Japanese(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val kanji: String?,
    val hiragana: String,
    // todo korean id
    val korean: List<String?>,
    /** 동사, 형용사, 명사, 등등 구분타입 **/ // todo enum
    val partOfSpeech: Int,
    val exampleSentence: List<String?>,
    val isFavorite: Boolean = false,
    val createdAt: Long,
    val lastStudiedAt: Long,
)
