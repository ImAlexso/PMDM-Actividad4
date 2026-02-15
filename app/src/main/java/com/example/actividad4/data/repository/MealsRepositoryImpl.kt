package com.example.actividad4.data.repository

import com.example.actividad4.data.remote.api.MealApiService
import com.example.actividad4.data.remote.mapper.toDomain
import com.example.actividad4.domain.model.Meal
import com.example.actividad4.domain.model.MealDetail

class MealsRepositoryImpl(
    private val api: MealApiService
) : MealsRepository {

    override suspend fun getMealsByCategory(category: String): List<Meal> {
        val response = api.getMealsByCategory(category)  // devuelve MealListResponseDto
        val dtoList = response.meals.orEmpty()           // List<MealDto> (o emptyList)
        return dtoList.map { it.toDomain() }             // List<Meal>
    }

    override suspend fun getMealDetail(id: String): MealDetail {
        val response = api.getMealDetail(id)             // devuelve MealDetailResponseDto
        val dto = response.meals?.firstOrNull()
            ?: throw IllegalStateException("No se encontró detalle para ese id")
        return dto.toDomain()                            // MealDetail
    }
}
