package com.aas.moviecatalog.ui.tvshow

interface TvShowInterface {
    fun showLoading()
    fun hideLoading()
    fun tvData(tv: TvShowResponseModel)
}