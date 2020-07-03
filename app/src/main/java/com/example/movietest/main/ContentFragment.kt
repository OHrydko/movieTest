package com.example.movietest.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.movietest.databinding.FragmentContentBinding
import com.example.movietest.util.DownloadImageTask


class ContentFragment : Fragment() {
    private lateinit var binding: FragmentContentBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var fragmentActivity: FragmentActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentActivity = this.activity!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mainViewModel = ViewModelProvider(fragmentActivity).get(MainViewModel::class.java)
        binding = FragmentContentBinding.inflate(inflater, container, false).apply {
            viewModel = mainViewModel
            lifecycleOwner = this@ContentFragment
        }
        if (mainViewModel.id != -1)
            getMovieData(mainViewModel.id)
        return binding.root
    }

    private fun getMovieData(id: Int) {
        mainViewModel.getOverview(id).observe(this@ContentFragment, Observer { overview ->
            if (overview != null) {
                val url = "https://image.tmdb.org/t/p/w500" + overview.poster_path
                DownloadImageTask(binding.image)
                    .execute(url)
                binding.apply {
                    title.text = overview.original_title
                    date.text = overview.release_date
                }
                binding.overview.text = overview.overview
                val genres: List<String> = overview.genres.map { it.name }
                val directors: List<String> = overview.production_companies.map { it.name }
                binding.genres.text = genres.joinToString(separator = ", ")
                binding.directors.text = directors.joinToString(separator = ", ")

            }
        })
    }

}