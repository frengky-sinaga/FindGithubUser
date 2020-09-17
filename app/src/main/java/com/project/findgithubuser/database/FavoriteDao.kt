package com.project.findgithubuser.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.project.findgithubuser.entity.FavoriteEntity

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Query("SELECT * FROM favorites_table")
    fun cursorReadAll(): Cursor

    @Query("SELECT * FROM favorites_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<FavoriteEntity>>

    @Query("DELETE FROM favorites_table WHERE username= :username")
    suspend fun delete(username: String)

    @Query("DELETE FROM favorites_table")
    suspend fun deleteAllData()
}