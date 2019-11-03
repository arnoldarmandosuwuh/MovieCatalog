package com.aas.moviecatalog.movie

import android.util.Log
import com.aas.moviecatalog.api.ApiRepository
import com.aas.moviecatalog.api.MovieDBApi
import com.loopj.android.http.AsyncHttpClient.LOG_TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviePresenter(
    private val mInterface: MovieInterface,
    private val mApi: MovieDBApi
) {
    fun loadMovie() {
        mInterface.showLoading()
        mApi.loadMovie(ApiRepository.API_KEY).enqueue(object : Callback<MovieResponseModel> {
            override fun onFailure(call: Call<MovieResponseModel>, t: Throwable) {
                mInterface.hideLoading()
                Log.e(LOG_TAG, "${t.message}")
            }

            override fun onResponse(
                call: Call<MovieResponseModel>,
                response: Response<MovieResponseModel>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        mInterface.movieData(data)
                    }
                    mInterface.hideLoading()
                }
            }

        })
    }

    fun searchMovie(query: String) {
        mInterface.showLoading()
        mApi.searchMovie(ApiRepository.API_KEY, query)
            .enqueue(object : Callback<MovieResponseModel> {
                override fun onFailure(call: Call<MovieResponseModel>, t: Throwable) {
                    Log.e(LOG_TAG, "${t.message}")
                    mInterface.hideLoading()
                }

                override fun onResponse(
                    call: Call<MovieResponseModel>,
                    response: Response<MovieResponseModel>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            mInterface.movieData(data)
                        }
                        mInterface.hideLoading()
                    }
                }

            })
    }
}