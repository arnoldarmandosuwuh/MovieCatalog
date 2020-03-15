package com.aas.moviecatalog.repository.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.aas.moviecatalog.repository.db.DbOpenHelper
import com.aas.moviecatalog.repository.db.DbOpenHelper.Companion.AUTHORITY
import com.aas.moviecatalog.repository.db.FavoriteDb.Companion.TABLE_FAVORITE
import com.aas.moviecatalog.repository.db.database

class FavoriteProvider : ContentProvider() {

    private lateinit var dbOpenHelper: DbOpenHelper
    private val movieId = 1
    private val tvShowId = 2

    private val mUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        this.addURI(AUTHORITY, TABLE_FAVORITE, movieId)
        this.addURI(AUTHORITY, TABLE_FAVORITE, tvShowId)
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        dbOpenHelper.open()
        return when (mUriMatcher.match(uri)) {
            movieId -> dbOpenHelper.queryMovieProvider()
            tvShowId -> dbOpenHelper.queryTvShowProvider()
            else -> dbOpenHelper.queryMovieProvider()
        }
    }

    override fun onCreate(): Boolean {
        dbOpenHelper = context?.database!!
        return true
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

}