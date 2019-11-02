package com.aas.moviecatalog.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DbOpenHelper(context: Context, dbName: String = "dbFavorite.db") :
    ManagedSQLiteOpenHelper(context, dbName, null, 1) {
    companion object {
        private var instance: DbOpenHelper? = null

        @Synchronized
        fun getInstance(context: Context): DbOpenHelper {
            if (instance == null) {
                instance = DbOpenHelper(context.applicationContext)
            }
            return instance as DbOpenHelper
        }
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
