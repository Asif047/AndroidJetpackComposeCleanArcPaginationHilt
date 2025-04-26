package com.mmj.movieapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.cleanarchjetpackcomposepractice.ui.data.datasource.local.db.MovieDatabase
import com.example.cleanarchjetpackcomposepractice.ui.data.datasource.local.entity.toDomain
import com.example.cleanarchjetpackcomposepractice.ui.data.datasource.mediator.MovieRemoteMediator
import com.mmj.movieapp.core.app.Constants
import com.mmj.movieapp.data.datasource.remote.MovieRemoteDataSource
import com.mmj.movieapp.data.repository.paging.MoviePagingSource
import com.mmj.movieapp.domain.model.Movie
import com.mmj.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val db: MovieDatabase,
    private val remoteDataSource: MovieRemoteDataSource
) : MovieRepository {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.MAX_PAGE_SIZE),
            remoteMediator = MovieRemoteMediator(
                db = db,
                api = remoteDataSource
            ),
            pagingSourceFactory = { db.movieDao().getAllMoviesPaging() }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }
}



