package com.example.cleanarchjetpackcomposepractice.ui.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mmj.movieapp.data.model.remote.dto.response.MovieResponseDto
import com.mmj.movieapp.domain.model.Movie

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val originalTitle: String,
    val originalLanguage: String,
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)

fun MovieResponseDto.toEntity(): MovieEntity = MovieEntity(
    id = id,
    title = title.orEmpty(),
    originalTitle = originalTitle.orEmpty(),
    originalLanguage = originalLanguage.orEmpty(),
    adult = adult,
    backdropPath = backdropPath.orEmpty(),
    genreIds = genreIds,
    overview = overview.orEmpty(),
    popularity = popularity,
    posterPath = posterPath.orEmpty(),
    releaseDate = releaseDate.orEmpty(),
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount
)


fun MovieEntity.toDomain(): Movie = Movie(
    id = id,
    title = title,
    originalTitle = originalTitle,
    originalLanguage = originalLanguage,
    adult = adult,
    backdropPath = backdropPath,
    genreIds = genreIds,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount
)
