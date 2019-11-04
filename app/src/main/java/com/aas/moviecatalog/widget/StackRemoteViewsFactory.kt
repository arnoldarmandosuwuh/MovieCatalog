package com.aas.moviecatalog.widget

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.aas.moviecatalog.R
import com.aas.moviecatalog.api.ApiRepository
import com.aas.moviecatalog.db.FavoriteDb
import com.aas.moviecatalog.db.database
import com.aas.moviecatalog.model.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.picasso.Picasso
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import java.util.concurrent.ExecutionException

class StackRemoteViewsFactory(private val context: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private val data = mutableListOf<FavoriteModel>()

    override fun onCreate() {

    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getItemId(position: Int): Long = 0

    override fun onDataSetChanged() {
        loadData()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rViews = RemoteViews(context.packageName, R.layout.widget_item)

        try {
            val bitmap = Glide.with(context).asBitmap().load(ApiRepository.BASE_IMAGE_URL + data[position].posterPath)
                .apply(RequestOptions().centerCrop())
                .submit()
                .get()
            rViews.setImageViewBitmap(R.id.iv_widget, bitmap)
        }catch (e: InterruptedException){
            e.printStackTrace()
        } catch (e: ExecutionException){
            e.printStackTrace()
        }

        val bundle = Bundle()
        bundle.putString(FavoriteWidget.FILM_ID, data[position].id)
        bundle.putString(FavoriteWidget.FILM_TYPE, data[position].type)
        val intent = Intent()
        intent.putExtras(bundle)

        rViews.setOnClickFillInIntent(R.id.iv_widget, intent)
        return rViews
    }

    override fun getCount(): Int = data.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {

    }

    private fun loadData() {
        context.database.use {
            data.clear()
            val result = select(FavoriteDb.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteDb>())
            for (i in 0 until favorite.size){
                data.add(
                    FavoriteModel(
                        favorite[i].filmId,
                        favorite[i].posterPath,
                        favorite[i].filmType
                    )
                )
            }
        }
    }

}