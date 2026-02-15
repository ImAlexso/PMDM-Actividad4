package com.example.actividad4.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.actividad4.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository): ViewModel(){

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun login(email: String, pw: String){
        if(validateBlanks(email, pw)){
            _uiState.value= LoginUiState.Error("Campos Vacios")
            return
        }
        _uiState.value = LoginUiState.Loading
        viewModelScope.launch {
            val user = userRepository.login(email, pw)
            if(user == null){
                _uiState.value = LoginUiState.Error("Email o contraseña incorrectos")
            }else{
                _uiState.value= LoginUiState.Success(user.id.toLong())
            }
        }
    }

    private fun validateBlanks(email: String, pw: String): Boolean {
        return email.isBlank() || pw.isBlank()
    }
}