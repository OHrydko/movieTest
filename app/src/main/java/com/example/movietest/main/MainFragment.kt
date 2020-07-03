package com.example.movietest.main

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movietest.R
import com.example.movietest.adapter.MovieAdapter
import com.example.movietest.databinding.FragmentMainBinding
import com.example.movietest.model.Results
import com.example.movietest.util.LoadMoreCallback
import com.example.movietest.util.LoadMoreItemsRV


class MainFragment : Fragment(), MovieAdapter.OnItemClick {

    private lateinit var binding: FragmentMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var fragmentActivity: FragmentActivity
    private var currentPage = 1
    private var curText = ""


    //state 1 - scroll for add list, 0 - for set list
    private val scroll: Int = 1
    private val first: Int = 0
    private var state: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        fragmentActivity = this.activity!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = ViewModelProvider(fragmentActivity).get(MainViewModel::class.java)
        binding = FragmentMainBinding.inflate(inflater, container, false).apply {
            viewModel = mainViewModel
            lifecycleOwner = this@MainFragment
        }

        movieAdapter = MovieAdapter(ArrayList(), this)
        recyclerView = binding.recyclerView
        val layoutManagerRV = LinearLayoutManager(fragmentActivity)
        recyclerView.apply {
            layoutManager = layoutManagerRV
            setHasFixedSize(true)
            adapter = movieAdapter
        }
        val loadMoreItemsRV = LoadMoreItemsRV(5)
        loadMoreItemsRV.setView(recyclerView)
        loadMoreItemsRV.setLoadMore(object : LoadMoreCallback {
            override fun loadMore() {
                mainViewModel.isLoader.value = true
                currentPage += 1
                state = scroll
                if (!mainViewModel.isSearch) {
                    mainViewModel.movie(currentPage)
                } else {
                    mainViewModel.searchMovie(curText, currentPage)
                }
            }

        })
        mainViewModel.init()
        getListMovies()
        getSearchMovies()
        return binding.root
    }

    private fun getListMovies() {
        mainViewModel.getMovie(1)
            .observe(fragmentActivity, Observer {
                if (it != null) {
                    mainViewModel.isLoader.value = false
                    if (state == first) {
                        movieAdapter.setMovies(it.results)
                    } else {
                        movieAdapter.addMovies(it.results)
                    }

                }
            })
    }


    private fun getSearchMovies() {
        mainViewModel.getSearchMovies("", 1)
            .observe(fragmentActivity, Observer {
                if (it != null) {
                    mainViewModel.isLoader.value = false
                    if (state == first) {
                        movieAdapter.setMovies(it.results)
                    } else {
                        movieAdapter.addMovies(it.results)
                    }

                }
            })
    }

    override fun onClick(movie: Results) {
        mainViewModel.id = movie.id
        fragmentActivity.supportFragmentManager.beginTransaction()
            .replace(R.id.frame, ContentFragment())
            .addToBackStack(null)
            .commit()
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
                    mainViewModel.searchMovie(newText, 1)
                    curText = newText
                    state = first
                    currentPage = 1
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
                mainViewModel.movie(1)
                mainViewModel.isSearch = false
                currentPage = 1
                state = first
                searchView.setQuery("", false)
            }
        }
    }
}