package com.example.actividad4.ui.list

import com.example.actividad4.domain.model.Meal

sealed class MealsListUiState {
    data object Idle : MealsListUiState()
    data object Loading : MealsListUiState()
    data class Success(val meals: List<Meal>, val category: String) : MealsListUiState()
    data class Error(val message: String) : MealsListUiState()
}
