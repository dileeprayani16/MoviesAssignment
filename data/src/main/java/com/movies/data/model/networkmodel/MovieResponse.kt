package com.movies.data.model.networkmodel

import com.google.gson.annotations.SerializedName
import com.movies.data.model.MovieItem

data class MovieResponse(
    @SerializedName("page") val page: Int? = null,
    @SerializedName("results") val movies: List<Movie>? = null
)

data class Movie(
    @SerializedName("adult") val adult: Boolean? = null,
    @SerializedName("backdrop_path") val backdropPath: String? = null,
    @SerializedName("genre_ids") val genreIds: List<Long>? = null,
    @SerializedName("id") val id: Long,
    @SerializedName("original_language") val originalLanguage: String? = null,
    @SerializedName("original_title") val originalTitle: String? = null,
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("popularity") val popularity: Float? = null,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("video") val video: Boolean? = null,
    @SerializedName("vote_average") val voteAverage: Float? = null,
    @SerializedName("vote_count") val voteCount: Long? = null
) {
    fun toMovieItem() = MovieItem(
        id = id,
        title = title,
        posterPath = posterPath,
        releaseDate = releaseDate,
        popularity = popularity
    )

    fun toDbMovie() = com.movies.database.db.movie.Movie(
        id = id,
        adult = adult,
        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}