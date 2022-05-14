package com.example.mobilliumcase.model

data class MovieDetail(
    val backdrop_path: String,
    val id: Int,
    val overview: String,
    val release_date: String,
    val title: String,
    val imdb_id: String,
    val vote_average: Double
)