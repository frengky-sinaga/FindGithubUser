package com.project.consumerapp.repository

import androidx.lifecycle.LiveData
import com.project.consumerapp.database.FavoriteDao
import com.project.consumerapp.entity.FavoriteEntity

class FavoriteRepository(private val favoritesDao: FavoriteDao) {

    companion object{
        private const val SCHEME = "scheme"
        private const val AUTHORITY = "com.project.findgithubuser"
        private const val TABLE = "favorites_table"
    }

    val readAllData: LiveData<List<FavoriteEntity>> = favoritesDao.readAllData()

    fun readAll() = favoritesDao.readAll()

    suspend fun addFavorite(favorite: FavoriteEntity) {
        favoritesDao.addFavorite(favorite)
    }

    suspend fun deleteFavorite(username: String) {
        favoritesDao.delete(username)
    }

    suspend fun deleteAllData() {
        favoritesDao.deleteAllData()
    }
}