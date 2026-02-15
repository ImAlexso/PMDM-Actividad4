package com.example.actividad4.data.remote.mapper

import com.example.actividad4.data.remote.dto.MealDetailDto
import com.example.actividad4.data.remote.dto.MealDto
import com.example.actividad4.domain.model.Meal
import com.example.actividad4.domain.model.MealDetail

fun MealDto.toDomain(): Meal =
    Meal(
        id = idMeal,
        name = strMeal,
        imageUrl = strMealThumb
    )

fun MealDetailDto.toDomain(): MealDetail =
    MealDetail(
        id = idMeal,
        name = strMeal,
        imageUrl = strMealThumb,
        instructions = strInstructions ?: ""
    )
