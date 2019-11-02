package com.aas.moviecatalog.detail

data class DetailMovieModel(
    val title: String,
    val poster_path: String,
    val release_date: String,
    val overview: String
)

data class DetailTvShowModel(
    val first_air_date: String,
    val name: String,
    val overview: String,
    val poster_path: String
)