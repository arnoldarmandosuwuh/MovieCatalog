package com.aas.moviecatalog.favorite.favtvshow

import com.aas.moviecatalog.model.TvShow

interface FavoriteTvShowInterface {
    fun showLoading()
    fun hideLoading()
    fun favoriteTvShow(tv: List<TvShow>)
}