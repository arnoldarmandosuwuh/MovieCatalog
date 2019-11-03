package com.aas.moviecatalog.api

import com.aas.moviecatalog.detail.DetailMovieModel
import com.aas.moviecatalog.detail.DetailTvShowModel
import com.aas.moviecatalog.movie.MovieResponseModel
import com.aas.moviecatalog.tvshow.TvShowResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDBApi {
    @GET("discover/movie")
    fun loadMovie(
        @Query("api_key") api_key: String,
        @Query("language") language: String = "en-US"
    ): Call<MovieResponseModel>

    @GET("discover/tv")
    fun loadTVShow(
        @Query("api_key") api_key: String,
        @Query("language") language: String = "en-US"
    ): Call<TvShowResponseModel>

    @GET("movie/{id}")
    fun loadMovieDetail(
        @Path("id") id: String,
        @Query("api_key") api_key: String,
        @Query("language") language: String = "en-US"
    ): Call<DetailMovieModel>

    @GET("tv/{id}")
    fun loadTVShowDetail(
        @Path("id") id: String,
        @Query("api_key") api_key: String,
        @Query("language") language: String = "en-US"
    ): Call<DetailTvShowModel>

    @GET("search/movie")
    fun searchMovie(
        @Query("api_key") api_key: String,
        @Query("query") query: String
    ): Call<MovieResponseModel>

    @GET("search/tv")
    fun searchTVShow(
        @Query("api_key") api_key: String,
        @Query("query") query: String
    ): Call<TvShowResponseModel>
}