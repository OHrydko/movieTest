package com.example.movietest.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.movietest.R
import com.example.movietest.databinding.MainItemBinding
import com.example.movietest.model.Results
import java.io.InputStream
import java.net.URL
import java.util.*


class MovieAdapter(
    private val movies: ArrayList<Results>,
    private val onItemClick: OnItemClick
) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    interface OnItemClick {
        fun onClick(movie: Results?)
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

    fun setMovies(movie: ArrayList<Results>?) {
        movie?.let { movies.addAll(it) }
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(val itemBinding: MainItemBinding) :
        ViewHolder(itemBinding.root)

    class DownloadImageTask(var bmImage: ImageView) :
        AsyncTask<String?, Void?, Bitmap?>() {
        override fun doInBackground(vararg params: String?): Bitmap? {
            val urldisplay = params[0]
            var mIcon11: Bitmap? = null
            try {
                val `in`: InputStream = URL(urldisplay).openStream()
                mIcon11 = BitmapFactory.decodeStream(`in`)
            } catch (e: Exception) {
                Log.e("Error", "error")
                e.printStackTrace()
            }
            return mIcon11
        }

        override fun onPostExecute(result: Bitmap?) {
            bmImage.setImageBitmap(result)
        }

    }
}