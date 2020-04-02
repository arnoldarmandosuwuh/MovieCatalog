package com.aas.moviecatalog.ui.detail

data class DetailMovieModel(
    val title: String,
    val poster_path: String,
    val release_date: String,
    val production_companies: List<ProductionCompanyModel>,
    val overview: String,
    val backdrop_path: String,
    val runtime: String
)

data class DetailTvShowModel(
    val episode_run_time: List<Int>,
    val first_air_date: String,
    val name: String,
    val overview: String,
    val poster_path: String,
    val backdrop_path: String,
    val production_companies: List<ProductionCompanyModel>
)

data class ProductionCompanyModel(val name: String)