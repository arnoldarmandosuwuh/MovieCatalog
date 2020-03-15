package com.aas.moviecatalog.ui.favorite.favtvshow

import com.aas.moviecatalog.repository.model.TvShow

interface FavoriteTvShowInterface {
    fun showLoading()
    fun hideLoading()
    fun favoriteTvShow(tv: List<TvShow>)
}