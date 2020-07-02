package com.example.movietest.model

import com.google.gson.annotations.SerializedName
import java.util.*


data class Movie(

    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: ArrayList<Results>,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("total_results") val total_results: Int
)