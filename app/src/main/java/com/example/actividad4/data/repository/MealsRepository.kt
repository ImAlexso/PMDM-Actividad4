package com.example.actividad4.data.repository

import com.example.actividad4.domain.model.Meal
import com.example.actividad4.domain.model.MealDetail

interface MealsRepository {
    suspend fun getMealsByCategory(category: String): List<Meal>
    suspend fun getMealDetail(id: String): MealDetail
}
