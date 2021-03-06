package com.aas.favoriteapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.database.ContentObserver
import android.database.Cursor
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.aas.favoriteapp.db.DbOpenHelper
import com.aas.favoriteapp.db.FavoriteDb
import com.aas.favoriteapp.model.Movie
import com.aas.favoriteapp.adapter.MovieAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity(), LoadMoviesCallback {

    companion object {
        const val MOVIE = "movie"
        const val TV = "tv"
    }

    private var movies = mutableListOf<Movie>()
    private lateinit var mAdapter: MovieAdapter
    private lateinit var dataObserver: DataObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMovie.addItemDecoration(
            DividerItemDecoration(
                rvMovie.context,
                DividerItemDecoration.VERTICAL
            )
        )
        rvMovie.layoutManager = LinearLayoutManager(this)
        mAdapter = MovieAdapter(this, movies)
        rvMovie.adapter = mAdapter

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        dataObserver = DataObserver(handler, this)
        contentResolver.registerContentObserver(DbOpenHelper.CONTENT_URI, true, dataObserver)
        GetData(this, this).execute()
    }

    override fun postExecute(cursor: Cursor) {
        val cursorData = mapCursorToArrayList(cursor)
        if (cursorData.isNotEmpty()) {
            movies.addAll(cursorData)
            mAdapter.notifyDataSetChanged()
        } else {
            tvNoData.visibility = View.VISIBLE
        }
    }

    internal class DataObserver(handler: Handler, private val context: Context) :
        ContentObserver(handler) {
        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            GetData(context, context as MainActivity).execute()
        }
    }

    internal class GetData(context: Context, callback: LoadMoviesCallback) :
        AsyncTask<Void, Void, Cursor>() {
        private val weakContent = WeakReference(context)
        private val weakCallback = WeakReference(callback)

        override fun doInBackground(vararg params: Void?): Cursor? {
            return weakContent.get()?.contentResolver?.query(
                DbOpenHelper.CONTENT_URI, null, "${FavoriteDb.FILM_TYPE} = ?",
                arrayOf(MOVIE), null
            )
        }

        override fun onPostExecute(result: Cursor?) {
            super.onPostExecute(result)
            if (result != null) {
                weakCallback.get()?.postExecute(result)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_movie -> {
                var intent: Intent = Intent()
                intent.component =
                    ComponentName("com.aas.moviecatalog", "com.aas.moviecatalog.MainActivity")
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}


interface LoadMoviesCallback {
    fun postExecute(cursor: Cursor)
}

fun mapCursorToArrayList(cursor: Cursor): List<Movie> {
    val movie = mutableListOf<Movie>()

    while (cursor.moveToNext()) {
        val id = cursor.getString(cursor.getColumnIndexOrThrow(FavoriteDb.FILM_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(FavoriteDb.FILM_TITLE))
        val poster_path = cursor.getString(cursor.getColumnIndexOrThrow(FavoriteDb.POSTER_PATH))
        val overview = cursor.getString(cursor.getColumnIndexOrThrow(FavoriteDb.OVERVIEW))
        val release_date = cursor.getString(cursor.getColumnIndexOrThrow(FavoriteDb.RELEASE_DATE))
        movie.add(Movie(id, title, poster_path, overview, release_date))
    }
    return movie
}
