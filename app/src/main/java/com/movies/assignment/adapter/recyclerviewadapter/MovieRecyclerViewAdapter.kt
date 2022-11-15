package com.movies.assignment.adapter.recyclerviewadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movies.assignment.databinding.LayoutRecyclerViewItemMovieBinding
import com.movies.assignment.extension.listen
import com.movies.assignment.listener.OnRecyclerViewItemClick
import com.movies.data.model.MovieItem

class MovieRecyclerViewAdapter(private val onRecyclerViewItemClick: OnRecyclerViewItemClick?) :
    RecyclerView.Adapter<MovieRecyclerViewAdapter.MoviesViewHolder>() {

    private var movieListItems = mutableListOf<MovieItem>()

    inner class MoviesViewHolder(val binding: LayoutRecyclerViewItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutRecyclerViewItemMovieBinding.inflate(inflater, parent, false)
        return MoviesViewHolder(binding).listen { position, _ ->
            onRecyclerViewItemClick?.onItemClick(position)
        }
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500/${movieListItems[position].posterPath}")
            .centerCrop().into(holder.binding.ivThumbnail)
        holder.binding.tvTitle.text = movieListItems[position].title
        holder.binding.tvPopularity.text = movieListItems[position].popularity.toString()
        if (movieListItems[position].releaseDate.toString().length >= 4) {
            holder.binding.tvReleaseYear.text =
                movieListItems[position].releaseDate.toString().substring(0, 4)
        }
        holder.binding.tvGenre.text =
            movieListItems[position].details?.genres?.map { it.name }?.joinToString().toString()
    }

    override fun getItemCount() = movieListItems.size

    fun setData(movieItems: List<MovieItem>) {
        this.movieListItems.clear()
        this.movieListItems.addAll(movieItems)
        notifyDataSetChanged()
    }
}