package com.example.movietest.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.movietest.model.Movie
import com.example.movietest.network.ApiService
import com.example.movietest.network.RetrofitApi
import retrofit2.Call
import retrofit2.Response


class Repository {
    private val movie: MutableLiveData<Movie> = MutableLiveData()

    fun getMovies(): MutableLiveData<Movie> {
        val retrofitApi = RetrofitApi()
        val apiService = retrofitApi.getClient()?.create(ApiService::class.java)
        val api_key = "8e0652e0b30813d0c7410a3498a2e053"

        apiService?.getList(api_key = api_key, media_type = "movie", time_window = "day")
            ?.enqueue(object : retrofit2.Callback<Movie> {
                override fun onResponse(
                    call: Call<Movie?>?,
                    response: Response<Movie?>
                ) {
                    val Movie: Movie? = response.body()
                    if (Movie != null) {
                        movie.postValue(Movie)
                    }
                }

                override fun onFailure(
                    call: Call<Movie?>?,
                    t: Throwable
                ) {
                    Log.d("throwable", "throwable")
                }
            })
        return movie
    }
}