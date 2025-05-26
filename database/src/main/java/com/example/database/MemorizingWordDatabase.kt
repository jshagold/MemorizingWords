package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.dao.JapaneseDao
import com.example.database.model.Japanese


@Database(
    entities = [
        Japanese::class
    ],
    version = 1
)
abstract class MemorizingWordDatabase : RoomDatabase() {
    abstract fun japaneseDao(): JapaneseDao
}