package com.movies.network.remote

import com.movies.data.model.networkmodel.MovieDetailResponse
import com.movies.network.utils.Constants
import com.movies.data.model.networkmodel.MovieResponse
import com.movies.network.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET(Constants.POPULAR_MOVIES)
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<MovieResponse>

    @GET(Constants.MOVIE_DETAILS)
    suspend fun getMovieDetails(
        @Path("id") movieId: Long,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<MovieDetailResponse>
}