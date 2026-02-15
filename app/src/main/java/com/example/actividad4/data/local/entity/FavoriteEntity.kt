package com.example.actividad4.data.local.entity

import androidx.room.Entity

@Entity(
    tableName = "favorites",
    primaryKeys = ["userId", "mealId"]
)
data class FavoriteEntity(
    val userId: Long,
    val mealId: String,
    val mealName: String,
    val mealImageUrl: String
)
