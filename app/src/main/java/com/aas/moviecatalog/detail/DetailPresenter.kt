package com.aas.moviecatalog.detail

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.os.Build
import android.util.Log
import com.aas.moviecatalog.api.ApiRepository
import com.aas.moviecatalog.api.MovieDBApi
import com.aas.moviecatalog.db.FavoriteDb
import com.loopj.android.http.AsyncHttpClient.LOG_TAG
import com.aas.moviecatalog.db.database
import com.aas.moviecatalog.widget.WidgetService
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPresenter(
    private val mInterface: DetailInterface,
    private val mApi: MovieDBApi
) {
    fun loadMovieDetail(id: String) {
        mInterface.showLoading()
        mApi.loadMovieDetail(id, ApiRepository.API_KEY)
            .enqueue(object : Callback<DetailMovieModel> {
                override fun onFailure(call: Call<DetailMovieModel>, t: Throwable) {
                    mInterface.hideLoading()
                    Log.e(LOG_TAG, "${t.message}")
                }

                override fun onResponse(
                    call: Call<DetailMovieModel>,
                    response: Response<DetailMovieModel>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            mInterface.showMovie(data)
                        }
                        mInterface.hideLoading()
                    }
                }

            })
    }

    fun loadTvShowDetail(id: String) {
        mInterface.showLoading()
        mApi.loadTVShowDetail(id, ApiRepository.API_KEY)
            .enqueue(object : Callback<DetailTvShowModel> {
                override fun onFailure(call: Call<DetailTvShowModel>, t: Throwable) {
                    mInterface.hideLoading()
                    Log.e(LOG_TAG, "${t.message}")
                }

                override fun onResponse(
                    call: Call<DetailTvShowModel>,
                    response: Response<DetailTvShowModel>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            mInterface.showTvShow(data)
                        }
                        mInterface.hideLoading()
                    }
                }

            })
    }

    fun addFavorite(
        context: Context,
        filmId: String,
        filmType: String,
        filmTitle: String,
        posterPath: String,
        overview: String,
        releaseDate: String
    ) {
        try {
            context.database.use {
                insert(
                    FavoriteDb.TABLE_FAVORITE,
                    FavoriteDb.FILM_ID to filmId,
                    FavoriteDb.FILM_TYPE to filmType,
                    FavoriteDb.FILM_TITLE to filmTitle,
                    FavoriteDb.POSTER_PATH to posterPath,
                    FavoriteDb.OVERVIEW to overview,
                    FavoriteDb.RELEASE_DATE to releaseDate
                )
            }
            context.toast("Favorite").show()
            updateWidget(context)
        } catch (e: SQLiteConstraintException) {
            context.toast(e.localizedMessage).show()
        }
    }

    fun removeFavorite(context: Context, filmId: String) {
        try {
            context.database.use {
                delete(
                    FavoriteDb.TABLE_FAVORITE,
                    "(FILM_ID = {id})",
                    "id" to filmId
                )
            }
            context.toast("Unfavorite").show()
            updateWidget(context)
        } catch (e: SQLiteConstraintException) {
            context.toast(e.localizedMessage).show()
        }
    }

    fun setFavorite(context: Context?, filmId: String): Boolean {
        var isFav = false

        context?.database?.use {
            val result = select(FavoriteDb.TABLE_FAVORITE)
                .whereArgs(
                    "(FILM_ID = {id})",
                    "id" to filmId
                )
            val fav = result.parseList(classParser<FavoriteDb>())
            if (fav.isNotEmpty()){
                isFav = true
            }
        }
        return isFav
    }

    private fun updateWidget(context: Context){
        val jobId = (0 until 200).random()
        val milis: Long = 10000
        val serviceComponent = ComponentName(context, WidgetService::class.java)
        val builder = JobInfo.Builder(jobId, serviceComponent)
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            builder.setMinimumLatency(milis)
        }
        else{
            builder.setPeriodic(milis)
        }
        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.schedule(builder.build())
    }
}