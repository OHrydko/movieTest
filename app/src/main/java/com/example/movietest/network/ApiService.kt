package com.example.movietest.network


import com.example.movietest.model.Movie
import com.example.movietest.model.information.Overview
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("3/trending/{media_type}/{time_window}")
    fun getList(
        @Path("media_type") media_type: String,
        @Path("time_window") time_window: String,
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): Call<Movie>

    @GET("3/search/movie")
    fun search(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Call<Movie>

    @GET("3/movie/{movie_id}")
    fun getAllData(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): Call<Overview>

}