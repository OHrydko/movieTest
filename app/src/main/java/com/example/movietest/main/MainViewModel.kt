package com.example.movietest.main

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movietest.model.Movie
import com.example.movietest.repository.Repository

class MainViewModel : ViewModel() {
    var isLoader: MutableLiveData<Boolean> = MutableLiveData()
    var movies: MutableLiveData<Boolean> = MutableLiveData()
    private lateinit var repository: Repository

    fun init() {
        repository = Repository()
    }

    fun getMovie() : MutableLiveData<Movie> {
        return repository.getMovies()
    }
}