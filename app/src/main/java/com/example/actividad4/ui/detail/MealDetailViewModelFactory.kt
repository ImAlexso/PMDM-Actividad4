package com.example.actividad4.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.actividad4.data.repository.FavoritesRepository
import com.example.actividad4.data.repository.MealsRepository

class MealDetailViewModelFactory(
    private val mealsRepository: MealsRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MealDetailViewModel(mealsRepository, favoritesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
