package com.project.findgithubuser.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.project.findgithubuser.database.FavoriteDao
import com.project.findgithubuser.database.FavoriteDatabase
import java.lang.UnsupportedOperationException

class ContentProvider : ContentProvider() {

    companion object{
        private const val authority = "com.project.findgithubuser.provider"
        private const val table = "favorites_table"
        private const val id = 1
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(authority, table, id)
        }
    }

    private val dao: FavoriteDao by lazy {
        FavoriteDatabase.getDatabase(requireNotNull(context)).favoriteDao()
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when(uriMatcher.match(uri)){
            id -> dao.cursorReadAll()
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        throw UnsupportedOperationException()
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException()
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        throw UnsupportedOperationException()
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException()
    }
}
