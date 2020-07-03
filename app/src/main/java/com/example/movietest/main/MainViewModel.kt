package com.example.movietest.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movietest.model.Movie
import com.example.movietest.repository.Repository

class MainViewModel : ViewModel() {
    var isLoader: MutableLiveData<Boolean> = MutableLiveData()
    private lateinit var repository: Repository

    fun init() {
        repository = Repository()
    }

    fun getMovie(): MutableLiveData<Movie> {
        return repository.getMovies()
    }

    fun getSearchMovies(query: String, page: Int): MutableLiveData<Movie> {
        return repository.getSearchList(query, page)
    }
}