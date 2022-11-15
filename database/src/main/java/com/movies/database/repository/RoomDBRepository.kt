package com.movies.database.repository

import com.movies.database.db.movie.Movie
import com.movies.database.db.movie.MovieDao
import javax.inject.Inject

class RoomDBRepository @Inject constructor(
    private val movieDao: MovieDao
) {
    fun deleteAllMovies() = movieDao.deleteRecords()
    fun getAllMovies() = movieDao.getAll()
    fun getMovieById(id: Long) = movieDao.get(id)
    suspend fun insertMovies(movies: List<Movie>) = movieDao.insert(movies)
}