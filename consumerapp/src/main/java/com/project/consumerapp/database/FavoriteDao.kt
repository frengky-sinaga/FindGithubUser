package com.project.consumerapp.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.project.consumerapp.entity.FavoriteEntity

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Query("SELECT * FROM favorites_table ORDER BY username ASC")
    fun readAll(): Cursor

    @Query("SELECT * FROM favorites_table ORDER BY username ASC")
    fun readAllData(): LiveData<List<FavoriteEntity>>

    @Query("DELETE FROM favorites_table WHERE username= :username")
    suspend fun delete(username: String)

    @Query("DELETE FROM favorites_table")
    suspend fun deleteAllData()
}