package com.example.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.database.model.Japanese

@Dao
interface JapaneseDao {

    @Upsert
    fun upsertWord(word: Japanese)

    @Upsert
    suspend fun upsertWordList(vararg word: Japanese)

    @Delete
    suspend fun deleteWord(word: Japanese)

    @Query("DELETE FROM japanese WHERE id = :id")
    suspend fun deleteWordById(id: Long)

    @Query("SELECT * FROM japanese WHERE id = :id")
    fun getWordById(id: Long): Japanese

    @Query("SELECT * FROM japanese")
    fun getAllWords(): List<Japanese>

    @Query("SELECT * FROM japanese LIMIT :pageSize OFFSET (:page - 1) * :pageSize")
    fun getWordListByPage(page: Int, pageSize: Int): List<Japanese>

    @Query("SELECT * FROM japanese ORDER BY :element LIMIT :pageSize OFFSET (:page - 1) * :pageSize")
    fun getWordListByPageAndElement(page: Int, pageSize: Int, element: String = "kanji"): List<Japanese>

    @Query("SELECT * FROM japanese ORDER BY kanji LIMIT :pageSize OFFSET (:page - 1) * :pageSize")
    fun getWordListByPageAndKanji(page: Int, pageSize: Int): List<Japanese>

    @Query("SELECT * FROM japanese ORDER BY hiragana LIMIT :pageSize OFFSET (:page - 1) * :pageSize")
    fun getWordListByPageAndHiragana(page: Int, pageSize: Int): List<Japanese>

    @Query("SELECT * FROM japanese ORDER BY korean LIMIT :pageSize OFFSET (:page - 1) * :pageSize")
    fun getWordListByPageAndKorean(page: Int, pageSize: Int): List<Japanese>
}