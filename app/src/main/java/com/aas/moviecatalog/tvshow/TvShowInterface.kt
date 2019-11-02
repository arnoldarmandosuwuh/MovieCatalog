package com.aas.moviecatalog.tvshow

interface TvShowInterface {
    fun showLoading()
    fun hideLoading()
    fun tvData(tv: TvShowResponseModel)
}