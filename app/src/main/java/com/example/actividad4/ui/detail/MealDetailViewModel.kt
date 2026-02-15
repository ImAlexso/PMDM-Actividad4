package com.example.actividad4.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.actividad4.data.local.entity.FavoriteEntity
import com.example.actividad4.data.repository.FavoritesRepository
import com.example.actividad4.data.repository.MealsRepository
import com.example.actividad4.domain.model.MealDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MealDetailViewModel(
    private val mealsRepository: MealsRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MealDetailUiState>(MealDetailUiState.Idle)
    val uiState: StateFlow<MealDetailUiState> = _uiState

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    private var lastLoadedDetail: MealDetail? = null

    fun loadMealDetail(userId: Long, mealId: String) {
        _uiState.value = MealDetailUiState.Loading

        viewModelScope.launch {
            try {
                val detail = mealsRepository.getMealDetail(mealId)
                lastLoadedDetail = detail
                _uiState.value = MealDetailUiState.Success(detail)

                _isFavorite.value = favoritesRepository.isFavorite(userId, mealId)
            } catch (e: Exception) {
                _uiState.value = MealDetailUiState.Error(e.message ?: "Error cargando detalle")
            }
        }
    }

    fun toggleFavorite(userId: Long) {
        val d = lastLoadedDetail ?: return

        viewModelScope.launch {
            val currentlyFav = favoritesRepository.isFavorite(userId, d.id)
            if (currentlyFav) {
                favoritesRepository.removeFavorite(userId, d.id)
                _isFavorite.value = false
            } else {
                favoritesRepository.addFavorite(
                    FavoriteEntity(
                        userId = userId,
                        mealId = d.id,
                        mealName = d.name,
                        mealImageUrl = d.imageUrl
                    )
                )
                _isFavorite.value = true
            }
        }
    }
}
