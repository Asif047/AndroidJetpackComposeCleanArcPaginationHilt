package com.mmj.movieapp.core.di

import android.content.Context
import androidx.room.Room
import com.example.cleanarchjetpackcomposepractice.ui.data.datasource.local.db.MovieDatabase
import com.mmj.movieapp.core.network.MovieApi
import com.mmj.movieapp.data.datasource.remote.MovieRemoteDataSource
import com.mmj.movieapp.data.datasource.remote.MovieRemoteDataSourceImpl
import com.mmj.movieapp.data.repository.MovieRepositoryImpl
import com.mmj.movieapp.domain.repository.MovieRepository
import com.mmj.movieapp.domain.usecase.GetMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesMovieRemoteDataSource(
        api: MovieApi
    ): MovieRemoteDataSource {
        return MovieRemoteDataSourceImpl(api)
    }

    @Singleton
    @Provides
    fun providesMovieRepository(
        movieDatabase: MovieDatabase, // Use MovieDatabase here
        movieRemoteDataSource: MovieRemoteDataSource
    ): MovieRepository {
        return MovieRepositoryImpl(movieDatabase, movieRemoteDataSource)
    }

    @Singleton
    @Provides
    fun providesGetMoviesUseCase(
        movieRepository: MovieRepository
    ): GetMoviesUseCase {
        return GetMoviesUseCase(movieRepository)
    }
}
