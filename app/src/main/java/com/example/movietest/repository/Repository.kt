package com.example.movietest.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.movietest.model.Movie
import com.example.movietest.model.information.Overview
import com.example.movietest.network.ApiService
import com.example.movietest.network.RetrofitApi
import retrofit2.Call
import retrofit2.Response


class Repository {
    private val movie: MutableLiveData<Movie> = MutableLiveData()
    private val searchMovie: MutableLiveData<Movie> = MutableLiveData()
    private val overview: MutableLiveData<Overview> = MutableLiveData()

    fun getMovies(page: Int): MutableLiveData<Movie> {
        val retrofitApi = RetrofitApi()
        val apiService = retrofitApi.getClient()?.create(ApiService::class.java)
        val api_key = "8e0652e0b30813d0c7410a3498a2e053"

        apiService?.getList(
            api_key = api_key,
            media_type = "movie",
            time_window = "day",
            page = page
        )
            ?.enqueue(object : retrofit2.Callback<Movie> {
                override fun onResponse(
                    call: Call<Movie?>?,
                    response: Response<Movie?>
                ) {
                    val movies: Movie? = response.body()
                    if (movies != null) {
                        movie.postValue(movies)
                    }
                }

                override fun onFailure(
                    call: Call<Movie?>?,
                    t: Throwable
                ) {
                    Log.d("throwable", "getMoviesRepository")
                }
            })
        return movie
    }

    fun getSearchList(query: String, page: Int): MutableLiveData<Movie> {
        val retrofitApi = RetrofitApi()
        val apiService = retrofitApi.getClient()?.create(ApiService::class.java)
        val api_key = "8e0652e0b30813d0c7410a3498a2e053"

        apiService?.search(api_key = api_key, language = "en-US", query = query, page = page)
            ?.enqueue(object : retrofit2.Callback<Movie> {
                override fun onResponse(
                    call: Call<Movie?>?,
                    response: Response<Movie?>
                ) {
                    val movies: Movie? = response.body()
                    if (movies != null) {
                        searchMovie.postValue(movies)
                    }
                }

                override fun onFailure(
                    call: Call<Movie?>?,
                    t: Throwable
                ) {
                    Log.d("throwable", "getSearchListRepository")
                }
            })
        return searchMovie
    }

    fun getOverview(id: Int): MutableLiveData<Overview> {
        val retrofitApi = RetrofitApi()
        val apiService = retrofitApi.getClient()?.create(ApiService::class.java)
        val api_key = "8e0652e0b30813d0c7410a3498a2e053"

        apiService?.getAllData(api_key = api_key, language = "en-US", movie_id = id)
            ?.enqueue(object : retrofit2.Callback<Overview> {
                override fun onResponse(
                    call: Call<Overview?>?,
                    response: Response<Overview?>
                ) {
                    val overviews: Overview? = response.body()
                    if (overviews != null) {
                        overview.postValue(overviews)
                    }
                }

                override fun onFailure(
                    call: Call<Overview?>?,
                    t: Throwable
                ) {
                    Log.d("throwable", "getSearchListRepository")
                }
            })
        return overview
    }


}