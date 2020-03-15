package com.aas.moviecatalog.ui.favorite.favtvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aas.moviecatalog.repository.model.TvShow

class FavoriteTvShowViewModel : ViewModel(){
    private var favoriteTv = MutableLiveData<List<TvShow>>()

    fun setTv(tv: List<TvShow>){
        favoriteTv.postValue(tv)
    }

    fun getTv() : LiveData<List<TvShow>> {
        return favoriteTv
    }
}