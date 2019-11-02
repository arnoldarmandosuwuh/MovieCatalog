package com.aas.moviecatalog.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieViewModel: ViewModel() {
    private var listMovie = MutableLiveData<MovieResponseModel>()

    fun setMovie(movie: MovieResponseModel){
        listMovie.postValue(movie)
    }
    fun getMovie() : LiveData<MovieResponseModel>{
        return listMovie
    }
}