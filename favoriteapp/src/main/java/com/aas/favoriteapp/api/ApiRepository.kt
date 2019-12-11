package com.aas.favoriteapp.api

import com.aas.favoriteapp.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiRepository {

    var BASE_IMAGE_URL: String = BuildConfig.POSTER_URL

}