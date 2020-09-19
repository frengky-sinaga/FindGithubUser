package com.project.findgithubuser.repository

import androidx.lifecycle.LiveData
import com.project.findgithubuser.database.FavoriteDao
import com.project.findgithubuser.entity.FavoriteEntity

class FavoriteRepository(private val favoritesDao: FavoriteDao) {

    val readAllData: LiveData<List<FavoriteEntity>> = favoritesDao.readAllData()

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