package com.example.atry.database

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun listToString(list: List<String>?): String? {
        return list?.joinToString(separator = ",")
    }

    @TypeConverter
    fun stringToList(list: String?): List<String>? {
        return list?.split(",")
    }
}