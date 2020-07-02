package com.example.movietest.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movietest.adapter.MovieAdapter
import com.example.movietest.databinding.FragmentMainBinding
import com.example.movietest.model.Results


class MainFragment : Fragment(), MovieAdapter.OnItemClick {

    private lateinit var binding: FragmentMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding = FragmentMainBinding.inflate(inflater, container, false).apply {
            viewModel = mainViewModel
            lifecycleOwner = this@MainFragment
        }

        movieAdapter = MovieAdapter(ArrayList(), this)
        recyclerView = binding.recyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
        mainViewModel.init()
        getListMovies()
        return binding.root
    }

    fun getListMovies() {
        mainViewModel.getMovie()
            .observe(this@MainFragment, Observer {
                if (it != null) {
                    movieAdapter.setMovies(it.results)
                }
            })

    }

    override fun onClick(movie: Results?) {
        TODO("Not yet implemented")
    }
}