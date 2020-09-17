package com.project.findgithubuser.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.project.findgithubuser.entity.FavoriteEntity
import com.project.findgithubuser.database.FavoriteDatabase
import com.project.findgithubuser.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FavoriteRepository
    val readAllData: LiveData<List<FavoriteEntity>>

    init {
        val favoriteDao = FavoriteDatabase.getDatabase(application).favoriteDao()
        repository = FavoriteRepository(favoriteDao)
        readAllData = repository.readAllData
    }

    fun addFavorite(favorite: FavoriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFavorite(favorite)
        }
    }

    fun deleteFavorite(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavorite(username)
        }
    }

    fun deleteAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllData()
        }
    }
}