package com.aas.moviecatalog.ui.detail

interface DetailInterface {
    fun showLoading()
    fun hideLoading()
    fun showMovie(movie: DetailMovieModel)
    fun showTvShow(tv: DetailTvShowModel)
}