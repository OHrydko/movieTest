package com.example.movietest.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movietest.model.Movie
import com.example.movietest.model.information.Overview
import com.example.movietest.repository.Repository

class MainViewModel : ViewModel() {
    var isLoader: MutableLiveData<Boolean> = MutableLiveData()
    private lateinit var repository: Repository
    var id: Int = -1
    var isSearch = false


    fun init() {
        repository = Repository()
    }

    fun getMovie(page: Int): MutableLiveData<Movie> {
        return repository.getMovies(page)
    }

    fun getSearchMovies(query: String, page: Int): MutableLiveData<Movie> {
        return repository.getSearchList(query, page)
    }

    fun getOverview(id: Int): MutableLiveData<Overview> {
        return repository.getOverview(id)
    }

    fun movie(page: Int) {
        isSearch = false
        getMovie(page)
    }

    fun searchMovie(query: String, page: Int) {
        isSearch = true
        getSearchMovies(query, page)
    }

}