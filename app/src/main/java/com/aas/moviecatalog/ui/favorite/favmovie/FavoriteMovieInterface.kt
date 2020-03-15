package com.aas.moviecatalog.ui.favorite.favmovie

import com.aas.moviecatalog.repository.model.Movie

interface FavoriteMovieInterface {
    fun showLoading()
    fun hideLoading()
    fun favoriteMovie(movie: List<Movie>)
}