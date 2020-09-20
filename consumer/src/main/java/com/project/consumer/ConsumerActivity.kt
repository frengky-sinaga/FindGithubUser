package com.project.consumer

import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log

class ConsumerActivity : AppCompatActivity() {

    companion object {
        private const val authority = "com.project.findgithubuser"
        private const val table = "favorites_table"
        private const val content = "content"

        private val uri: Uri = Uri.Builder()
            .scheme(content)
            .authority(authority)
            .appendPath(table)
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consumer)

        getData()
    }

    private fun getData() {
        try {
            Log.d("AppLog", "Uri: $uri")
            val handlerThread = HandlerThread("DataObserver")
            handlerThread.start()
            val handler = Handler(handlerThread.looper)

            val myObserver = object : ContentObserver(handler){
                override fun onChange(selfChange: Boolean) {
                    super.onChange(selfChange)
                    loadDataAsync()
                }
            }
            contentResolver.registerContentObserver(uri, true, myObserver)
        }catch (e: Throwable){
            Log.d("AppLog", "Throw: ${e.message}")
        }

    }

    private fun loadDataAsync(){
        Log.d("AppLog", "Connect")
    }
}
