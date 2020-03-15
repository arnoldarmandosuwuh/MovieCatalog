package com.aas.moviecatalog.repository.model

data class Movie(
    val id: String,
    val title: String,
    val poster_path: String,
    val overview: String,
    val release_date: String
)