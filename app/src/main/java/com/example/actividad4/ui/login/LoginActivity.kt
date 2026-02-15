package com.example.actividad4.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.actividad4.MyApplication
import com.example.actividad4.databinding.ActivityLoginBinding
import com.example.actividad4.ui.list.MealsListActivity
import com.example.actividad4.ui.register.RegisterActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel : LoginViewModel by viewModels {
        val appContainer = (application as MyApplication).appContainer
        LoginViewModelFactory(appContainer.userRepository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when(state){
                    is LoginUiState.Error ->{
                        binding.btnLogin.isEnabled = true
                        binding.btnRegister.isEnabled = true
                        Toast.makeText(this@LoginActivity, state.message, Toast.LENGTH_SHORT).show()
                    }

                    is LoginUiState.Success -> {
                        Toast.makeText(this@LoginActivity, "Login Correcto", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@LoginActivity, MealsListActivity::class.java)
                        intent.putExtra(MealsListActivity.EXTRA_USER_ID, state.userId)
                        startActivity(intent)

                        finish()
                    }
                    LoginUiState.Loading ->{
                        binding.btnLogin.isEnabled =false
                        binding.btnRegister.isEnabled =false
                    }

                    LoginUiState.Idle ->{
                        binding.btnLogin.isEnabled =true
                        binding.btnRegister.isEnabled=true
                    }
                }
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        binding.btnLogin.setOnClickListener {
                login()
        }
        binding.btnRegister.setOnClickListener {
                register()
        }
    }

    fun register() {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun login(){
        val(email, password) = readCredentials()
        viewModel.login(email, password)
    }

    private fun readCredentials(): Pair<String, String>{
        val email = binding.EtEmail.text.toString().trim().lowercase()
        val password = binding.EtPassword.text.toString().trim()
        return Pair(email,password)
    }

}