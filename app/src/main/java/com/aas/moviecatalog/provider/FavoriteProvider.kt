package com.aas.moviecatalog.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.aas.moviecatalog.db.DbOpenHelper

class FavoriteProvider : ContentProvider(){

    private lateinit var dbOpenHelper: DbOpenHelper
    private val movieId = 1
    private val tvShowId = 2

//    private val mUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
//        this.addURI(AUTHO)
//    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getType(uri: Uri): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}