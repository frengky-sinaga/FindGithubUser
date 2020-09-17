package com.project.findgithubuser.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.project.findgithubuser.database.FavoriteDatabase
import com.project.findgithubuser.repository.FavoriteRepository
import com.project.findgithubuser.utils.log

class AppProvider : ContentProvider() {

    companion object {
        private const val AUTHORITY = "com.project.findgithubuser"
        private const val TABLE = "favorites_table"
        private const val APP = 1
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, TABLE, APP)
        }
    }

    private lateinit var repository: FavoriteRepository

    override fun onCreate(): Boolean {
        log("Content Provider Created")
        val dao = FavoriteDatabase.getDatabase(requireNotNull(context)).favoriteDao()
        repository = FavoriteRepository(dao)

        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            APP -> repository.cursorReadAll()
            else -> null
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
