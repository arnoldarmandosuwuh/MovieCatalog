package com.aas.moviecatalog.ui.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import com.aas.moviecatalog.MainActivity
import com.aas.moviecatalog.R
import com.aas.moviecatalog.ui.detail.DetailActivity

/**
 * Implementation of App Widget functionality.
 */
class FavoriteWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object{
        const val ACTION = "ACTION_FOR_WIDGET"
        const val FILM_ID = "FILM_ID"
        const val FILM_TYPE = "FILM_TYPE"

        internal fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val intent = Intent(context, FavoriteWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))

            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.favorite_widget)
            views.setRemoteAdapter(R.id.stack_view, intent)
            views.setEmptyView(R.id.stack_view, R.id.empty_view)

            val widgetText = context.resources.getString(R.string.appwidget_text)
            views.setTextViewText(R.string.appwidget_text, widgetText)

            val toastIntent = Intent(context, FavoriteWidget::class.java)
            toastIntent.action = ACTION
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))

            val pendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            views.setPendingIntentTemplate(R.id.stack_view, pendingIntent)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent != null){
            if (intent.action == ACTION){
                val id =intent.getStringExtra(FILM_ID)
                val type = intent.getStringExtra(FILM_TYPE)

                val i = Intent(context, DetailActivity::class.java)
                i.putExtra(MainActivity.DATA_EXTRA, id)
                i.putExtra(MainActivity.TYPE, type)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context?.startActivity(i)
            }
        }
    }
}