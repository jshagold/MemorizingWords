package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.database.converter.ListStringConverter
import com.example.database.dao.JapaneseDao
import com.example.database.model.Japanese


@Database(
    entities = [
        Japanese::class
    ],
    version = 1
)
@TypeConverters(ListStringConverter::class)
abstract class MemorizingWordDatabase : RoomDatabase() {
    abstract fun japaneseDao(): JapaneseDao
}