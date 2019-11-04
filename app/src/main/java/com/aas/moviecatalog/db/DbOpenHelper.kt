package com.aas.moviecatalog.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
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

    fun open(){
        database = instance!!.writableDatabase
    }


    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.createTable(
                FavoriteDb.TABLE_FAVORITE, true,
                FavoriteDb.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteDb.FILM_ID to TEXT,
                FavoriteDb.FILM_TYPE to TEXT,
                FavoriteDb.FILM_TITLE to TEXT,
                FavoriteDb.POSTER_PATH to TEXT
            )
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (db != null) {
            db.dropTable(FavoriteDb.TABLE_FAVORITE, true)
        }
    }
}

val Context.database: DbOpenHelper
    get() = DbOpenHelper.getInstance(applicationContext)
