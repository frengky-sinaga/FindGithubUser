package com.project.consumerapp.entity

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_table")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val img: Bitmap,
    val url: String,
    val username: String,
    val email: String
)