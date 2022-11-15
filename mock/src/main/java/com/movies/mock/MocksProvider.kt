package com.movies.mock

import android.content.Context
import com.google.gson.Gson
import com.movies.data.model.networkmodel.MovieDetailResponse
import com.movies.data.model.networkmodel.MovieResponse

object MocksProvider {

    fun getPopularMovies(context: Context): MovieResponse = Gson().fromJson(
        readJson(context, "popular_movies.json"),
        MovieResponse::class.java
    )

    fun getMovieDetails(context: Context): MovieDetailResponse = Gson().fromJson(
        readJson(context, "movie_details.json"),
        MovieDetailResponse::class.java
    )

    private fun readJson(context: Context, fileName: String): String {
        return try {
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ex: Exception) {
            ex.printStackTrace()
            ""
        }
    }
}