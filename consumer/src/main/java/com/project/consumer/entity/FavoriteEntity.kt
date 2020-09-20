package com.project.consumer.entity

import android.graphics.Bitmap

data class FavoriteEntity(
    val img: Bitmap,
    val email: String,
    val username: String
)