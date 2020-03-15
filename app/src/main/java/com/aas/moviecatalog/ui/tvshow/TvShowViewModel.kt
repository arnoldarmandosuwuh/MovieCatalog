package com.aas.moviecatalog.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TvShowViewModel: ViewModel(){
    private var listTv = MutableLiveData<TvShowResponseModel>()

    fun setMovie(tv: TvShowResponseModel){
        listTv.postValue(tv)
    }
    fun getMovie() : LiveData<TvShowResponseModel> {
        return listTv
    }
}