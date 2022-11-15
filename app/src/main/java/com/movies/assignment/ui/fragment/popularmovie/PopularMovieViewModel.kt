package com.movies.assignment.ui.fragment.popularmovie

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.movies.data.model.MovieItem
import com.movies.data.model.networkmodel.MovieDetailResponse
import com.movies.data.model.networkmodel.MovieResponse
import com.movies.database.repository.RoomDBRepository
import com.movies.network.remote.Repository
import com.movies.network.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMovieViewModel @Inject constructor(
    private val repository: Repository,
    private val roomDBRepository: RoomDBRepository,
    application: Application
) : AndroidViewModel(application) {

    private var currentPage: Int = 1

    private val _response: MutableLiveData<List<MovieItem>> = MutableLiveData()
    val response: LiveData<List<MovieItem>> = _response

    private val _responseMovieDetail: MutableLiveData<MovieDetailResponse> = MutableLiveData()
    val responseMovieDetail: LiveData<MovieDetailResponse> = _responseMovieDetail

    private val _loading: MutableLiveData<Int> = MutableLiveData(View.GONE)
    val loading: LiveData<Int> = _loading

    fun fetchMovies(isLoadMore: Boolean = false) = viewModelScope.launch {
        if (isLoading().not()) {
            if (isLoadMore) {
                currentPage++
            }
            showLoading()
            repository.getPopularMovies(currentPage).collectLatest { values ->
                hideLoading()
                values.data?.movies?.forEach {
                    fetchMovieDetails(it.id)
                }
                _response.value = values.data?.movies?.map { it.toMovieItem() }
                saveMoviesToDatabase(values)
            }
        }
    }

    private fun fetchMovieDetails(id: Long) = viewModelScope.launch {
        repository.getMovieDetails(id).collectLatest { values ->
            _responseMovieDetail.value = values.data
        }
    }

    private fun saveMoviesToDatabase(values: NetworkResult<MovieResponse>) {
        if (values is NetworkResult.Success) {
            viewModelScope.launch {
                values.data?.movies?.map { it.toDbMovie() }?.let {
                    roomDBRepository.insertMovies(it)
                }
            }
        }
    }

    fun fetchMoviesFromDatabase() = viewModelScope.launch {
        showLoading()
        _response.value = roomDBRepository.getAllMovies().map {
            MovieItem.fromDbMovie(it)
        }
        hideLoading()
    }

    private fun isLoading() = loading.value == View.VISIBLE

    private fun showLoading() {
        _loading.postValue(View.VISIBLE)
    }

    private fun hideLoading() {
        _loading.postValue(View.GONE)
    }
}