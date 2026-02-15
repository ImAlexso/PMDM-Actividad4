package com.example.actividad4.ui.register

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.actividad4.MyApplication
import com.example.actividad4.databinding.ActivityRegisterBinding
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels {
        val appContainer = (application as MyApplication).appContainer
        RegisterViewModelFactory(appContainer.userRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is RegisterUiState.Error -> {
                        binding.btnRegisterUser.isEnabled = true
                        Toast.makeText(this@RegisterActivity, state.message, Toast.LENGTH_SHORT).show()
                    }
                    RegisterUiState.Success -> {
                        Toast.makeText(this@RegisterActivity, "Usuario creado", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    RegisterUiState.Loading -> {
                        binding.btnRegisterUser.isEnabled = false
                    }
                    RegisterUiState.Idle -> {
                        binding.btnRegisterUser.isEnabled = true
                    }
                }
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnRegisterUser.setOnClickListener {
                registerUser()
        }
    }

    private fun registerUser() {
        val email = readEmail()
        val (password, secondPassword) = readPasswords()

        viewModel.register(email, password, secondPassword)
    }
    private fun readPasswords(): Pair<String, String>{
        val password = binding.EtPassword.text.toString().trim()
        val secondPassword = binding.EtSecondPassword.text.toString().trim()
        return Pair(password,secondPassword)
    }
    private fun readEmail(): String{
        val email = binding.EtEmail.text.toString().trim().lowercase()
        return email
    }
}