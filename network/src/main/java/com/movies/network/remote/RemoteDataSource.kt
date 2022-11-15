package com.movies.network.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val movieService: MovieService
){
    suspend fun getPopularMovies(page: Int) = movieService.getPopularMovies(page)
    suspend fun getMovieDetails(id: Long) = movieService.getMovieDetails(id)
}