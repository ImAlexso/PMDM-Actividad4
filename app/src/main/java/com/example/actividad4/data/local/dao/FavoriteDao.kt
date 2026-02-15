package com.example.actividad4.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.actividad4.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(fav: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE userId = :userId AND mealId = :mealId")
    suspend fun deleteFavorite(userId: Long, mealId: String)

    @Query("SELECT * FROM favorites WHERE userId = :userId ORDER BY mealName ASC")
    fun getFavoritesByUser(userId: Long): LiveData<List<FavoriteEntity>>

    @Query("SELECT COUNT(*) FROM favorites WHERE userId = :userId AND mealId = :mealId")
    suspend fun isFavorite(userId: Long, mealId: String): Int
}
