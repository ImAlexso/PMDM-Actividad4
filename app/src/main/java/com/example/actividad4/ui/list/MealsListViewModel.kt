package com.example.actividad4.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.actividad4.data.repository.MealsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MealsListViewModel(
    private val mealsRepository: MealsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MealsListUiState>(MealsListUiState.Idle)
    val uiState: StateFlow<MealsListUiState> = _uiState

    fun loadCategory(category: String) {
        _uiState.value = MealsListUiState.Loading

        viewModelScope.launch {
            try {
                val meals = mealsRepository.getMealsByCategory(category)
                _uiState.value = MealsListUiState.Success(meals = meals, category = category)
            } catch (e: Exception) {
                _uiState.value = MealsListUiState.Error(e.message ?: "Error cargando recetas")
            }
        }
    }
}
