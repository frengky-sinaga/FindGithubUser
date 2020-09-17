package com.project.consumerapp.viewmodels

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.project.consumerapp.database.Converters
import com.project.consumerapp.entity.FavoriteEntity

class FavoriteViewModel : ViewModel() {

    companion object {
        private const val AUTHORITY = "com.project.findgithubuser"
        private const val TABLE = "favorites_table"
        private const val SCHEME = "content"

        val uri: Uri = Uri.Builder()
            .scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE)
            .build()

        /*private const val _id = "id"
        private const val _img = "img"
        private const val _url = "url"
        private const val _username = "username"
        private const val _email = "email"*/
    }

    fun contentProvider(context: Context) {
        //val _uri = Uri.parse("content://$AUTHORITY/$TABLE")
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        if (cursor == null) {
            Log.d("AppLog", "Cursor Null")
        } else {
            Log.d("AppLog", "Cursor not null")
        }
        cursor?.close()
    }

    /*private fun setData(cursor: Cursor?): ArrayList<FavoriteEntity>? {
        val list = arrayListOf<FavoriteEntity>()
        cursor?.apply {
            while (moveToNext()) {
                val byteArray = getBlob(getColumnIndexOrThrow(_email))
                val converters = Converters()
                val convertByteArray = converters.toBitmap(byteArray)
                list.add(
                    FavoriteEntity(
                        getInt(getColumnIndexOrThrow(_id)),
                        convertByteArray,
                        getString(getColumnIndexOrThrow(_url)),
                        getString(getColumnIndexOrThrow(_username)),
                        getString(getColumnIndexOrThrow(_email))
                    )
                )
            }
        }
        return list
    }*/
}