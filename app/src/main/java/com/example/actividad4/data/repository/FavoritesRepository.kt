package com.example.actividad4.data.repository

import androidx.lifecycle.LiveData
import com.example.actividad4.data.local.entity.FavoriteEntity

interface FavoritesRepository {
    fun getFavoritesByUser(userId: Long): LiveData<List<FavoriteEntity>>
    suspend fun addFavorite(fav: FavoriteEntity)
    suspend fun removeFavorite(userId: Long, mealId: String)
    suspend fun isFavorite(userId: Long, mealId: String): Boolean
}
