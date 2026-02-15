package com.example.actividad4.ui.detail

import com.example.actividad4.domain.model.MealDetail

sealed class MealDetailUiState {
    data object Idle : MealDetailUiState()
    data object Loading : MealDetailUiState()
    data class Success(val mealDetail: MealDetail) : MealDetailUiState()
    data class Error(val message: String) : MealDetailUiState()
}
