package com.example.cleanarchjetpackcomposepractice.ui.data.datasource.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.cleanarchjetpackcomposepractice.ui.data.datasource.local.db.MovieDatabase
import com.example.cleanarchjetpackcomposepractice.ui.data.datasource.local.entity.MovieEntity
import com.example.cleanarchjetpackcomposepractice.ui.data.datasource.local.entity.MovieRemoteKeys
import com.example.cleanarchjetpackcomposepractice.ui.data.datasource.local.entity.toEntity
import com.mmj.movieapp.core.app.Constants
import com.mmj.movieapp.data.datasource.remote.MovieRemoteDataSource

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val db: MovieDatabase,
    private val api: MovieRemoteDataSource
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull() ?: return MediatorResult.Success(true)
                db.remoteKeysDao().remoteKeysMovieId(lastItem.id)?.nextKey
                    ?: return MediatorResult.Success(true)
            }
        }

        return try {
            val response = api.getMovies(Constants.MOVIE_API_KEY, page)
            val movies = response.results?.map { it.toEntity() } ?: emptyList()
            val endOfPaginationReached = movies.isEmpty()

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.remoteKeysDao().clearRemoteKeys()
                    db.movieDao().clearAll()
                }

                val keys = movies.map {
                    MovieRemoteKeys(
                        movieId = it.id,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (endOfPaginationReached) null else page + 1
                    )
                }

                db.remoteKeysDao().insertAll(keys)
                db.movieDao().insertAll(movies)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
