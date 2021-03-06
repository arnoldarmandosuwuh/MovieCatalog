package com.aas.moviecatalog.ui.favorite.favmovie

import android.content.Context
import com.aas.moviecatalog.MainActivity
import com.aas.moviecatalog.repository.db.FavoriteDb
import com.aas.moviecatalog.repository.db.database
import com.aas.moviecatalog.repository.model.Movie
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteMoviePresenter(private val mInterface: FavoriteMovieInterface) {
    private var listMovie = mutableListOf<Movie>()

    fun getFavoriteMovie(context: Context?) {
        mInterface.showLoading()

        context?.database?.use {
            listMovie.clear()

            val result = select(
                FavoriteDb.TABLE_FAVORITE
            ).whereArgs("${FavoriteDb.FILM_TYPE} = '${MainActivity.MOVIE}'")
            val favorite = result.parseList(classParser<FavoriteDb>())
            for (i in 0 until favorite.size){
                listMovie.add(Movie(
                    favorite[i].filmId,
                    favorite[i].filmTitle,
                    favorite[i].posterPath,
                    favorite[i].overview,
                    favorite[i].releaseDate
                ))
            }
        }
        mInterface.hideLoading()
        mInterface.favoriteMovie(listMovie)
    }
}