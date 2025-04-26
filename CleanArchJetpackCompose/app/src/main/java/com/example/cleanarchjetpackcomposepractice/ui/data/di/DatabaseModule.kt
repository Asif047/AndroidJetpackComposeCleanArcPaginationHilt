package com.example.cleanarchjetpackcomposepractice.ui.data.di

import android.content.Context
import androidx.room.Room
import com.example.cleanarchjetpackcomposepractice.ui.data.datasource.local.dao.MovieDao
import com.example.cleanarchjetpackcomposepractice.ui.data.datasource.local.dao.MovieRemoteKeysDao
import com.example.cleanarchjetpackcomposepractice.ui.data.datasource.local.db.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context.applicationContext, // Use applicationContext to avoid memory leaks
            MovieDatabase::class.java,
            "movie_db"
        ).build()
    }

    @Provides
    fun provideMovieDao(db: MovieDatabase): MovieDao = db.movieDao()

    @Provides
    fun provideRemoteKeysDao(db: MovieDatabase): MovieRemoteKeysDao = db.remoteKeysDao()
}
