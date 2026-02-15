package com.example.actividad4.data.remote.api

import com.example.actividad4.data.remote.dto.MealDetailResponseDto
import com.example.actividad4.data.remote.dto.MealListResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): MealListResponseDto

    @GET("lookup.php")
    suspend fun getMealDetail(@Query("i") id: String): MealDetailResponseDto
}
