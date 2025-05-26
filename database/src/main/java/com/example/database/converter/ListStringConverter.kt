package com.example.database.converter

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

object ListStringConverter {
    @TypeConverter
    fun fromList(list: List<String?>?): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun toList(data: String): List<String?> {
        return Json.decodeFromString(data)
    }
}