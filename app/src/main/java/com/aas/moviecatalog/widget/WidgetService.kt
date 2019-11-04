package com.aas.moviecatalog.widget

import android.app.job.JobParameters
import android.app.job.JobService
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.widget.RemoteViews
import com.aas.moviecatalog.R

class WidgetService : JobService(){
    override fun onStopJob(params: JobParameters?): Boolean {
        return false
    }

    override fun onStartJob(params: JobParameters?): Boolean {
       val manager = AppWidgetManager.getInstance(this)
        val view = RemoteViews(packageName, R.layout.favorite_widget)
        val widgetFavorite = ComponentName(this, FavoriteWidget::class.java)
        manager.updateAppWidget(widgetFavorite, view)
        jobFinished(params, false)
        return true
    }

}