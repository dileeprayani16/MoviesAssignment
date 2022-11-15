package com.movies.data.model

import com.movies.data.model.networkmodel.MovieDetailResponse
import com.movies.database.db.movie.Movie

data class MovieItem(
    val id: Long? = null,
    val title: String? = null,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val popularity: Float? = null,
    var details: MovieDetailResponse? = null,
) {
    companion object {
        fun fromDbMovie(movie: Movie) = MovieItem(
            id = movie.id,
            title = movie.title,
            posterPath = movie.posterPath,
            releaseDate = movie.releaseDate,
            popularity = movie.popularity
        )
    }
}