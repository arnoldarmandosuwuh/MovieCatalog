package com.aas.moviecatalog.favorite.favmovie

import com.aas.moviecatalog.model.Movie

interface FavoriteMovieInterface {
    fun showLoading()
    fun hideLoading()
    fun favoriteMovie(movie: List<Movie>)
}