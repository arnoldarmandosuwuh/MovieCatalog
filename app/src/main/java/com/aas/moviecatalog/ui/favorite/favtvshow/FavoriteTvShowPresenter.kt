package com.aas.moviecatalog.ui.favorite.favtvshow

import android.content.Context
import com.aas.moviecatalog.MainActivity
import com.aas.moviecatalog.repository.db.FavoriteDb
import com.aas.moviecatalog.repository.db.database
import com.aas.moviecatalog.repository.model.TvShow
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteTvShowPresenter (private val mInterface: FavoriteTvShowInterface){
    private var listTv = mutableListOf<TvShow>()

    fun getFavoriteTvShow(context: Context?) {
        mInterface.showLoading()

        context?.database?.use {
            listTv.clear()

            val result = select(
                FavoriteDb.TABLE_FAVORITE
            ).whereArgs("${FavoriteDb.FILM_TYPE} = '${MainActivity.TV}'")
            val favorite = result.parseList(classParser<FavoriteDb>())
            for (i in 0 until favorite.size){
                listTv.add(TvShow(
                    favorite[i].filmId,
                    favorite[i].filmTitle,
                    favorite[i].posterPath,
                    favorite[i].overview
                ))
            }
        }
        mInterface.hideLoading()
        mInterface.favoriteTvShow(listTv)
    }
}