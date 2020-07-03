package com.example.movietest.main

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movietest.R
import com.example.movietest.adapter.MovieAdapter
import com.example.movietest.databinding.FragmentMainBinding
import com.example.movietest.model.Results


class MainFragment : Fragment(), MovieAdapter.OnItemClick {

    private lateinit var binding: FragmentMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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

    private fun getListMovies() {
        mainViewModel.getMovie()
            .observe(this@MainFragment, Observer {
                if (it != null) {
                    movieAdapter.setMovies(it.results)
                }
            })
    }

    private fun getSearchMovies() {
        mainViewModel.getSearchMovies("", 1)
            .observe(this@MainFragment, Observer {
                if (it != null) {
                    mainViewModel.isLoader.postValue(false)
                    movieAdapter.setMovies(it.results)
                }
            })
    }

    override fun onClick(movie: Results?) {
        TODO("Not yet implemented")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
        val searchView =
            SearchView((context as MainActivity).supportActionBar?.themedContext ?: context)
        menu.findItem(R.id.action_search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty()) {
                    mainViewModel.isLoader.postValue(true)
                    getSearchMovies()
                    mainViewModel.getSearchMovies(newText, 1)
                }

                return true
            }
        })
        val clearButton: ImageView =
            searchView.findViewById(androidx.appcompat.R.id.search_close_btn)
        clearButton.setOnClickListener {
            if (searchView.query.isEmpty()) {
                searchView.isIconified = true
            } else {

                getListMovies()
                searchView.setQuery("", false)
            }
        }
    }
}