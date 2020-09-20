package com.project.findgithubuser.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.project.findgithubuser.database.FavoriteDao
import com.project.findgithubuser.database.FavoriteDatabase
import com.project.findgithubuser.utils.log

class ContentProvider : ContentProvider() {

    companion object {
        private const val authority = "com.project.findgithubuser"
        private const val table = "favorites_table"
        private const val id = 1

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(authority, table, id)
        }
    }

    private lateinit var dao: FavoriteDao

    override fun onCreate(): Boolean {
        dao = context?.let { FavoriteDatabase.getDatabase(it).favoriteDao() }!!
        log("Content Provider Created : $uriMatcher")
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            id -> dao.cursorReadAll()
            else -> throw IllegalArgumentException("Unknown Uri: $uri")
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }
}
