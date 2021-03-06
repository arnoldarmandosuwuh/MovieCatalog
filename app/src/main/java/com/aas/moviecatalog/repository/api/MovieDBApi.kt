package com.aas.moviecatalog.repository.api

import com.aas.moviecatalog.ui.detail.DetailMovieModel
import com.aas.moviecatalog.ui.detail.DetailTvShowModel
import com.aas.moviecatalog.ui.movie.MovieResponseModel
import com.aas.moviecatalog.ui.tvshow.TvShowResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDBApi {
    @GET("discover/movie")
    fun loadMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US"
    ): Call<MovieResponseModel>

    @GET("discover/tv")
    fun loadTVShow(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US"
    ): Call<TvShowResponseModel>

    @GET("movie/{id}")
    fun loadMovieDetail(
        @Path("id") id: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US"
    ): Call<DetailMovieModel>

    @GET("tv/{id}")
    fun loadTVShowDetail(
        @Path("id") id: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US"
    ): Call<DetailTvShowModel>

    @GET("search/movie")
    fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Call<MovieResponseModel>

    @GET("search/tv")
    fun searchTVShow(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Call<TvShowResponseModel>

    @GET("discover/movie")
    fun todayReleaseMovie(
        @Query("api_key") apiKey: String,
        @Query("primary_release_date.gte") primaryReleaseDateGte: String,
        @Query("primary_release_date.lte") primaryReleaseDateLte: String
    ) : Call<MovieResponseModel>
}