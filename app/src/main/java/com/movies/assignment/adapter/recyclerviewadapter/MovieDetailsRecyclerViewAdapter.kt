package com.movies.assignment.adapter.recyclerviewadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.movies.assignment.databinding.LayoutRecyclerViewItemMovieDetailBinding
import com.movies.assignment.extension.listen
import com.movies.assignment.listener.OnRecyclerViewItemClick
import com.movies.data.model.MovieDetailItem
import com.movies.data.utils.orDefaultPlaceHolder

class MovieDetailsRecyclerViewAdapter(private val onRecyclerViewItemClick: OnRecyclerViewItemClick?) :
    RecyclerView.Adapter<MovieDetailsRecyclerViewAdapter.MoviesViewHolder>() {

    private var movieDetailsListItems = mutableListOf<MovieDetailItem>()

    inner class MoviesViewHolder(val binding: LayoutRecyclerViewItemMovieDetailBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutRecyclerViewItemMovieDetailBinding.inflate(inflater, parent, false)
        return MoviesViewHolder(binding).listen { position, _ ->
            onRecyclerViewItemClick?.onItemClick(position)
        }
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.binding.tvKey.text = movieDetailsListItems[position].key
        holder.binding.tvValue.text = movieDetailsListItems[position].value.orDefaultPlaceHolder()
    }

    override fun getItemCount() = movieDetailsListItems.size

    fun setData(movieDetailsListItems: List<MovieDetailItem>) {
        this.movieDetailsListItems.clear()
        this.movieDetailsListItems.addAll(movieDetailsListItems)
        notifyDataSetChanged()
    }
}