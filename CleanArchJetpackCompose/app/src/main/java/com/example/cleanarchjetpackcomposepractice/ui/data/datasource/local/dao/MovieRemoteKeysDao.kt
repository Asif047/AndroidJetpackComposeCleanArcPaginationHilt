package com.example.cleanarchjetpackcomposepractice.ui.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cleanarchjetpackcomposepractice.ui.data.datasource.local.entity.MovieRemoteKeys

@Dao
interface MovieRemoteKeysDao {
    @Query("SELECT * FROM movie_remote_keys WHERE movieId = :id")
    suspend fun remoteKeysMovieId(id: Int): MovieRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<MovieRemoteKeys>)

    @Query("DELETE FROM movie_remote_keys")
    suspend fun clearRemoteKeys()
}
