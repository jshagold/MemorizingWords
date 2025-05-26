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
    fun deleteWord(word: Japanese)

    @Query("SELECT * FROM japanese")
    fun getAllWords(): List<Japanese>

}