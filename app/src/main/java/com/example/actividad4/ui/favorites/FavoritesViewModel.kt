package com.example.actividad4.ui.favorites

import androidx.lifecycle.ViewModel
import com.example.actividad4.data.repository.FavoritesRepository

class FavoritesViewModel(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    fun favorites(userId: Long) = favoritesRepository.getFavoritesByUser(userId)
}
