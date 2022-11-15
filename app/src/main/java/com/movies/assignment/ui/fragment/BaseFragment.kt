package com.movies.assignment.ui.fragment

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.movies.assignment.R

open class BaseFragment: Fragment() {

    private var canNavigateBack: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity()
            .onBackPressedDispatcher
            .addCallback {
                if (canNavigateBack) {
                    findNavController().navigateUp()
                }
            }
    }

    fun setupToolbar(title: String, canNavigateBack: Boolean = true) {
        requireActivity().findViewById<Toolbar>(R.id.toolbar)?.apply {
            this.title = title
        }
        this@BaseFragment.canNavigateBack = canNavigateBack
    }
}