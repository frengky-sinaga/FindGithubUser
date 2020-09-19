package com.project.consumer

import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    companion object {
        private const val authority = "com.project.findgithubuser.provider"
        private const val table = "favorites_table"
        private const val content = "content"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getData()
    }

    private fun getData() {
        val uri: Uri = Uri.Builder()
            .scheme(content)
            .authority(authority)
            .appendPath(table)
            .build()

        val contentResolver = this.contentResolver
        val cursor: Cursor? = contentResolver.query(
            uri,
            null,
            null,
            null,
            null
        )
        cursor?.close()
    }
}
