package com.aas.moviecatalog.detail

interface DetailInterface {
    fun showLoading()
    fun hideLoading()
    fun showMovie(movie: DetailMovieModel)
    fun showTvShow(tv: DetailTvShowModel)
}