package com.example.movietest.model.information

import com.google.gson.annotations.SerializedName


data class Language(

    @SerializedName("iso_639_1") val iso_639_1: String,
    @SerializedName("name") val name: String
)