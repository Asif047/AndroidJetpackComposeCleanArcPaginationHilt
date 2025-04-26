package com.example.cleanarchjetpackcomposepractice.ui.data.datasource.local.db

import androidx.room.TypeConverter

class GenreIdsConverter {
    @TypeConverter
    fun fromList(list: List<Int>): String = list.joinToString(",")

    @TypeConverter
    fun toList(data: String): List<Int> =
        if (data.isEmpty()) emptyList() else data.split(",").map { it.toInt() }
}