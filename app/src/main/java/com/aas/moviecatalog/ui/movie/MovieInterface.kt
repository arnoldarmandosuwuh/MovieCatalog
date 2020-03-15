package com.aas.moviecatalog.ui.movie

interface MovieInterface {
    fun showLoading()
    fun hideLoading()
    fun movieData(movie: MovieResponseModel)
}