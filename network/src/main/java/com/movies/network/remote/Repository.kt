package com.movies.network.remote

import android.app.Application
import com.movies.data.model.networkmodel.MovieDetailResponse
import com.movies.data.model.networkmodel.MovieResponse
import com.movies.mock.MocksProvider
import com.movies.network.BuildConfig
import com.movies.network.model.BaseApiResponse
import com.movies.network.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val application: Application
) : BaseApiResponse() {
    suspend fun getPopularMovies(page: Int): Flow<NetworkResult<MovieResponse>> {
        return flow {
            if (BuildConfig.ENVIRONMENT == "NETWORK") {
                emit(safeApiCall { remoteDataSource.getPopularMovies(page) })
            } else {
                emit(NetworkResult.Success(MocksProvider.getPopularMovies(application)))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieDetails(id: Long): Flow<NetworkResult<MovieDetailResponse>> {
        return flow {
            if (BuildConfig.ENVIRONMENT == "NETWORK") {
                emit(safeApiCall { remoteDataSource.getMovieDetails(id) })
            } else {
                emit(NetworkResult.Success(MocksProvider.getMovieDetails(application)))
            }
        }.flowOn(Dispatchers.IO)
    }
}