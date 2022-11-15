package com.movies.assignment.ui.fragment.moviedetails

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.movies.assignment.R
import com.movies.assignment.adapter.recyclerviewadapter.MovieDetailsRecyclerViewAdapter
import com.movies.assignment.databinding.FragmentMovieDetailsBinding
import com.movies.assignment.listener.OnRecyclerViewItemClick
import com.movies.assignment.ui.fragment.BaseFragment
import com.movies.data.model.MovieDetailItem
import com.movies.data.utils.orDefaultPlaceHolder
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment(), OnRecyclerViewItemClick {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val moviesDetails = mutableListOf<MovieDetailItem>()
    private val adapter = MovieDetailsRecyclerViewAdapter(this)
    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    override fun onStart() {
        super.onStart()
        setupToolbar(title = args.title, canNavigateBack = true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int) {
        if (moviesDetails[position].isLink && moviesDetails[position].value?.isNotBlank() == true) {
            openUrlInChrome(moviesDetails[position].value.orEmpty())
        }
    }

    private fun openUrlInChrome(url: String) {
        try {
            try {
                val uri: Uri = Uri.parse("googlechrome://navigate?url=$url")
                val i = Intent(Intent.ACTION_VIEW, uri)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(i)
            } catch (e: ActivityNotFoundException) {
                val uri: Uri = Uri.parse(url)
                val i = Intent(Intent.ACTION_VIEW, uri)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(i)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun initViews() {
        binding.rvDetails.adapter = adapter.apply {
            setData(moviesDetails)
        }
    }

    private fun initObservers() {
        args.details.let {
            (it.posterPath ?: it.backdropPath)?.let {
                Glide.with(requireContext()).load("https://image.tmdb.org/t/p/w500/$it")
                    .centerCrop().into(binding.ivBanner)
            } ?: run { binding.ivBanner.isVisible = false }

            moviesDetails.clear()
            moviesDetails.add(
                MovieDetailItem(
                    key = getString(R.string.key_overview), value = it.overview
                )
            )
            moviesDetails.add(
                MovieDetailItem(
                    key = getString(R.string.key_original_title), value = it.originalTitle
                )
            )
            moviesDetails.add(
                MovieDetailItem(
                    key = getString(R.string.key_original_language), value = it.originalLanguage
                )
            )
            moviesDetails.add(
                MovieDetailItem(
                    key = getString(R.string.key_is_adult),
                    value = if (it.adult == true) "Yes" else "No"
                )
            )
            moviesDetails.add(
                MovieDetailItem(
                    key = getString(R.string.key_popularity), value = it.popularity.toString()
                )
            )
            moviesDetails.add(
                MovieDetailItem(
                    key = getString(R.string.key_vote_average), value = it.voteAverage.toString()
                )
            )
            moviesDetails.add(
                MovieDetailItem(
                    key = getString(R.string.key_vote_count), value = it.voteCount.toString()
                )
            )
            moviesDetails.add(
                MovieDetailItem(
                    key = getString(R.string.key_genres),
                    value = it.genres?.map { it.name }?.joinToString().toString()
                        .orDefaultPlaceHolder()
                )
            )
            moviesDetails.add(
                MovieDetailItem(
                    key = getString(R.string.key_runtime), value = it.runtime.toString()
                )
            )
            moviesDetails.add(
                MovieDetailItem(
                    key = getString(R.string.key_home_page),
                    value = it.homePage.toString(),
                    isLink = true
                )
            )

            adapter.setData(moviesDetails)
        }
    }
}