package com.project.consumer

import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.consumer.adapter.ConsumerAdapter
import com.project.consumer.entity.FavoriteEntity
import com.project.consumer.utils.Converters
import kotlinx.android.synthetic.main.activity_consumer.*

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

    private lateinit var adapter: ConsumerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consumer)

        getData()
    }

    private fun getData() {
        val cursor = contentResolver.query(uri, null, null, null, null)
        if (cursor != null){
            val data = getDataCursor(cursor)
            showRecycleList(data)
        }
    }

    private fun getDataCursor(cursor: Cursor): ArrayList<FavoriteEntity>{
        val result = ArrayList<FavoriteEntity>()
        cursor.apply {
            while (moveToNext()){
                val img = getBlob(getColumnIndexOrThrow("img"))
                val converter = Converters()
                val imgConverted = converter.toBitmap(img)
                val email = getString(getColumnIndexOrThrow("email"))
                val username = getString(getColumnIndexOrThrow("username"))
                val favorite = FavoriteEntity(imgConverted, email, username)
                result.add(favorite)
            }
        }
        return result
    }

    private fun showRecycleList(data: ArrayList<FavoriteEntity>){
        adapter = ConsumerAdapter(ArrayList())
        rv_favorites.setHasFixedSize(true)
        rv_favorites.layoutManager = LinearLayoutManager(this)
        rv_favorites.adapter = adapter
        adapter.addNewData(data)
    }
}
