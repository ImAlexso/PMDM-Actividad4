package com.example.actividad4.data.firebase

import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class FavoritesFirebaseDataSource {

    private val ref = FirebaseDatabase.getInstance().reference

    suspend fun saveFavorite(
        userId: Long,
        mealId: String,
        mealName: String,
        mealImageUrl: String
    ) {
        val data = mapOf(
            "mealId" to mealId,
            "mealName" to mealName,
            "mealImageUrl" to mealImageUrl
        )

        ref.child("favorites")
            .child(userId.toString())
            .child(mealId)
            .setValue(data)
            .await()
    }

    suspend fun deleteFavorite(userId: Long, mealId: String) {
        ref.child("favorites")
            .child(userId.toString())
            .child(mealId)
            .removeValue()
            .await()
    }
}
