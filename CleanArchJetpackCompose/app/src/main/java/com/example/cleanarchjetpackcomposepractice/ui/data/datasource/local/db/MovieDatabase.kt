package com.example.cleanarchjetpackcomposepractice.ui.data.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cleanarchjetpackcomposepractice.ui.data.datasource.local.dao.MovieDao
import com.example.cleanarchjetpackcomposepractice.ui.data.datasource.local.dao.MovieRemoteKeysDao
import com.example.cleanarchjetpackcomposepractice.ui.data.datasource.local.entity.MovieRemoteKeys
import com.example.cleanarchjetpackcomposepractice.ui.data.datasource.local.entity.MovieEntity

@Database(
    entities = [MovieEntity::class, MovieRemoteKeys::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(GenreIdsConverter::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun remoteKeysDao(): MovieRemoteKeysDao
}
