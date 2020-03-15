package com.aas.moviecatalog.ui.detail

data class DetailMovieModel(
    val title: String,
    val poster_path: String,
    val release_date: String,
    val overview: String,
    val backdrop_path: String
)

data class DetailTvShowModel(
    val first_air_date: String,
    val name: String,
    val overview: String,
    val poster_path: String,
    val backdrop_path: String
)