package com.example.actividad4.ui.login

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val userId: Long) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}
