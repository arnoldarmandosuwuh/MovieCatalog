package com.aas.moviecatalog.ui.movie

import com.aas.moviecatalog.repository.model.Movie

data class MovieResponseModel(val results: List<Movie>)