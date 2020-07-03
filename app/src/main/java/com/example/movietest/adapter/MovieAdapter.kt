package com.example.movietest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.movietest.R
import com.example.movietest.databinding.MainItemBinding
import com.example.movietest.model.Results
import com.example.movietest.util.DownloadImageTask
import java.util.*


class MovieAdapter(
    private val movies: ArrayList<Results>,
    private val onItemClick: OnItemClick
) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    interface OnItemClick {
        fun onClick(movie: Results)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val binding: MainItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.main_item, parent, false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int
    ) {
        val movie: Results = movies[position]
        holder.itemBinding.name.text = movie.original_title
        val url = "https://image.tmdb.org/t/p/w500" + movie.poster_path
        DownloadImageTask(holder.itemBinding.image)
            .execute(url)

        holder.itemBinding.description.text = movie.overview
        holder.itemBinding.container.setOnClickListener { onItemClick.onClick(movie) }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun addMovies(movie: ArrayList<Results>?) {
        movie?.let { movies.addAll(it) }
        notifyDataSetChanged()
    }

    fun setMovies(movie: ArrayList<Results>?) {
        movies.clear()
        movie?.let { movies.addAll(it) }
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(val itemBinding: MainItemBinding) :
        ViewHolder(itemBinding.root)


}