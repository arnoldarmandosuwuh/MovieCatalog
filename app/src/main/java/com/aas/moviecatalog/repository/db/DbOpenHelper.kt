package com.aas.moviecatalog.repository.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import com.aas.moviecatalog.MainActivity
import org.jetbrains.anko.db.*

class DbOpenHelper(context: Context, dbName: String = "dbFavorite.db") :
    ManagedSQLiteOpenHelper(context, dbName, null, 1) {
    private lateinit var database: SQLiteDatabase

    companion object {

        const val AUTHORITY = "com.aas.moviecatalog"
        private const val SCHEME = "content"

        private var instance: DbOpenHelper? = null

        @Synchronized
        fun getInstance(context: Context): DbOpenHelper {
            if (instance == null) {
                instance = DbOpenHelper(context.applicationContext)
            }
            return instance as DbOpenHelper
        }

        val CONTENT_URI = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY).appendPath(FavoriteDb.TABLE_FAVORITE).build()
    }

    fun open() {
        database = instance!!.writableDatabase
    }

    fun queryMovieProvider(): Cursor {
        return database.query(
            FavoriteDb.TABLE_FAVORITE,
            null, "${FavoriteDb.FILM_TYPE} = ?", arrayOf(MainActivity.MOVIE),
            null, null, null
        )
    }

    fun queryTvShowProvider(): Cursor {
        return database.query(
            FavoriteDb.TABLE_FAVORITE,
            null, "${FavoriteDb.FILM_TYPE} = ?", arrayOf(MainActivity.TV),
            null, null, null
        )
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            FavoriteDb.TABLE_FAVORITE, true,
            FavoriteDb.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteDb.FILM_ID to TEXT,
            FavoriteDb.FILM_TYPE to TEXT,
            FavoriteDb.FILM_TITLE to TEXT,
            FavoriteDb.POSTER_PATH to TEXT,
            FavoriteDb.OVERVIEW to TEXT,
            FavoriteDb.RELEASE_DATE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(FavoriteDb.TABLE_FAVORITE, true)
    }
}

val Context.database: DbOpenHelper
    get() = DbOpenHelper.getInstance(applicationContext)
