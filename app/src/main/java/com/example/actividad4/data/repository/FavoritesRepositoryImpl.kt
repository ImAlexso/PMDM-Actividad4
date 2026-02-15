package com.example.actividad4.data.repository

import androidx.lifecycle.LiveData
import com.example.actividad4.data.firebase.FavoritesFirebaseDataSource
import com.example.actividad4.data.local.dao.FavoriteDao
import com.example.actividad4.data.local.entity.FavoriteEntity

class FavoritesRepositoryImpl(
    private val favoriteDao: FavoriteDao,
    private val firebase: FavoritesFirebaseDataSource
) : FavoritesRepository {

    override fun getFavoritesByUser(userId: Long): LiveData<List<FavoriteEntity>> =
        favoriteDao.getFavoritesByUser(userId)

    override suspend fun isFavorite(userId: Long, mealId: String): Boolean =
        favoriteDao.isFavorite(userId, mealId) > 0

    override suspend fun addFavorite(fav: FavoriteEntity) {
        // 1) Local SIEMPRE
        favoriteDao.insertFavorite(fav)

        // 2) Remoto (si falla, NO rompas app)
        try {
            firebase.saveFavorite(fav.userId, fav.mealId, fav.mealName, fav.mealImageUrl)
        } catch (_: Exception) { }
    }

    override suspend fun removeFavorite(userId: Long, mealId: String) {
        // 1) Local
        favoriteDao.deleteFavorite(userId, mealId)

        // 2) Remoto
        try {
            firebase.deleteFavorite(userId, mealId)
        } catch (_: Exception) { }
    }
}
