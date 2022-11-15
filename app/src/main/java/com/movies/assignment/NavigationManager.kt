package com.movies.assignment

import android.app.Activity
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.movies.data.model.networkmodel.MovieDetailResponse
import javax.inject.Inject

class NavigationManager @Inject constructor(
    private val activity: Activity
) {
    fun navigateToMovieDetailsFragment(title: String, details: MovieDetailResponse) {
        activity.findNavController(R.id.mainContainer).navigate(
            R.id.action_navigation_movies_to_navigation_movie_details, bundleOf(
                "details" to details,
                "title" to title
            )
        )
    }
}
