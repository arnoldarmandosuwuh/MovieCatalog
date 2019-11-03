package com.aas.moviecatalog.tvshow

import android.util.Log
import com.aas.moviecatalog.api.ApiRepository
import com.aas.moviecatalog.api.MovieDBApi
import com.loopj.android.http.AsyncHttpClient.LOG_TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvShowPresenter(
    private val mInterface: TvShowInterface,
    private val mApi: MovieDBApi
) {
    fun loadTvShow() {
        mInterface.showLoading()
        mApi.loadTVShow(ApiRepository.API_KEY).enqueue(object : Callback<TvShowResponseModel> {
            override fun onFailure(call: Call<TvShowResponseModel>, t: Throwable) {
                mInterface.hideLoading()
                Log.e(LOG_TAG, "${t.message}")
            }

            override fun onResponse(
                call: Call<TvShowResponseModel>,
                response: Response<TvShowResponseModel>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        mInterface.tvData(data)
                    }
                    mInterface.hideLoading()
                }
            }

        })
    }

    fun searchTvShow(query: String){
        mInterface.showLoading()
        mApi.searchTVShow(ApiRepository.API_KEY, query)
            .enqueue(object : Callback<TvShowResponseModel>{
                override fun onFailure(call: Call<TvShowResponseModel>, t: Throwable) {
                    mInterface.hideLoading()
                    Log.e(LOG_TAG, "${t.message}")
                }

                override fun onResponse(
                    call: Call<TvShowResponseModel>,
                    response: Response<TvShowResponseModel>
                ) {
                    if(response.isSuccessful){
                        val data = response.body()
                        if (data != null) {
                            mInterface.tvData(data)
                        }
                        mInterface.hideLoading()
                    }
                }

            })
    }

}