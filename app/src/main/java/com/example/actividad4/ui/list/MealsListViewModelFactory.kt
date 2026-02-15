package com.example.actividad4.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.actividad4.data.repository.MealsRepository

class MealsListViewModelFactory(
    private val mealsRepository: MealsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealsListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MealsListViewModel(mealsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
