package com.movies.assignment.ui.fragment.popularmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.movies.assignment.NavigationManager
import com.movies.assignment.R
import com.movies.assignment.adapter.recyclerviewadapter.MovieRecyclerViewAdapter
import com.movies.assignment.databinding.FragmentPopularMovieBinding
import com.movies.assignment.listener.OnRecyclerViewItemClick
import com.movies.assignment.ui.fragment.BaseFragment
import com.movies.data.model.MovieItem
import com.movies.network.utils.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PopularMovieFragment : BaseFragment(), OnRecyclerViewItemClick {

    var navigationManager: NavigationManager? = null
        @Inject set

    private val popularMovieViewModel by viewModels<PopularMovieViewModel>()
    private var _binding: FragmentPopularMovieBinding? = null
    private val binding get() = _binding!!

    private val moviesList = mutableListOf<MovieItem>()
    private val adapter = MovieRecyclerViewAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularMovieBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = popularMovieViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initViews()
        initListeners()
        initObservers()
    }

    override fun onStart() {
        super.onStart()
        setupToolbar(title = getString(R.string.app_name), canNavigateBack = false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int) {
        moviesList[position].details?.let {
            navigationManager?.navigateToMovieDetailsFragment(
                moviesList[position].title.orEmpty(), it
            )
        }
    }

    private fun initViews() {
        if (NetworkUtils.isNetworkAvailable(requireContext())) {
            popularMovieViewModel.fetchMovies()
        } else {
            popularMovieViewModel.fetchMoviesFromDatabase()
        }
        binding.rvMovies.adapter = adapter.apply {
            setData(moviesList)
        }
    }

    private fun initListeners() {
        binding.rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (recyclerView.canScrollVertically(1)
                        .not() && newState == RecyclerView.SCROLL_STATE_IDLE
                ) {
                    popularMovieViewModel.fetchMovies(true)
                }
            }
        })
    }

    private fun initObservers() {
        popularMovieViewModel.response.observe(viewLifecycleOwner) {
            moviesList.addAll(it)
            adapter.setData(moviesList)
        }

        popularMovieViewModel.responseMovieDetail.observe(viewLifecycleOwner) {
            moviesList.firstOrNull { movie -> movie.id == it.id }?.details = it
            adapter.setData(moviesList)
        }
    }
}