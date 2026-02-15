package com.example.actividad4.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.actividad4.data.local.entity.UserEntity
import com.example.actividad4.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val uiState = _uiState.asStateFlow()
    fun register(email: String, pw1: String, pw2: String) {
        if (validateBlanks(email, pw1, pw2)) {
            _uiState.value = RegisterUiState.Error("Campos Vacios")
            return
        }
        if (!validatePasswords(pw1, pw2)) {
            _uiState.value = RegisterUiState.Error("Contraseñas Diferentes")
            return
        }
        _uiState.value = RegisterUiState.Loading
        viewModelScope.launch {
            if (validateEmail(email)) {
                _uiState.value = RegisterUiState.Error("Email ya registrado")
            } else {
                val user = UserEntity(email = email, password = pw1)
                insertUser(user)
                _uiState.value = RegisterUiState.Success
            }
        }
    }

    private fun validateBlanks(email: String, password: String, secondPassword: String): Boolean {
        return email.isBlank() || password.isBlank() || secondPassword.isBlank()
    }

    private fun validatePasswords(pw1: String, pw2: String): Boolean {
        return pw1 == pw2
    }

    private suspend fun insertUser(user: UserEntity) {
        userRepository.registerUser(user)

    }

    private suspend fun validateEmail(email: String): Boolean {
        return userRepository.emailExists(email)
    }

}