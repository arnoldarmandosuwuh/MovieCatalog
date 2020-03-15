package com.aas.moviecatalog.repository.api

import com.aas.moviecatalog.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiRepository {
    var BASE_URL: String = BuildConfig.BASE_URL
    var API_KEY: String = BuildConfig.API_KEY
    var BASE_IMAGE_URL: String = BuildConfig.POSTER_URL

    fun create(): MovieDBApi {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        return retrofit.create(MovieDBApi::class.java)
    }
}