package com.example.actividad4.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.actividad4.MyApplication
import com.example.actividad4.databinding.ActivityMealDetailBinding
import com.example.actividad4.ui.list.MealsListActivity
import kotlinx.coroutines.launch

class MealDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealDetailBinding

    private var userId: Long = -1L
    private var mealId: String = ""

    private val viewModel: MealDetailViewModel by viewModels {
        val c = (application as MyApplication).appContainer
        MealDetailViewModelFactory(c.mealsRepository, c.favoritesRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMealDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ✅ Leer userId + mealId
        userId = intent.getLongExtra(MealsListActivity.EXTRA_USER_ID, -1L)
        mealId = intent.getStringExtra(MealsListActivity.EXTRA_MEAL_ID) ?: ""

        if (userId == -1L || mealId.isBlank()) {
            Toast.makeText(this, "Datos no recibidos", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        observeUi()
        observeFavorite()

        // ✅ Cargar detalle + comprobar favorito
        viewModel.loadMealDetail(userId, mealId)

        // ✅ Toggle favorito
        binding.iBtnFavourite.setOnClickListener {
            viewModel.toggleFavorite(userId)
        }
    }

    private fun observeUi() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    MealDetailUiState.Idle -> Unit
                    MealDetailUiState.Loading -> Unit
                    is MealDetailUiState.Success -> {
                        val d = state.mealDetail
                        binding.tvMealName.text = d.name
                        binding.ivMealImage.load(d.imageUrl) { crossfade(true) }
                        binding.tvInstructions.text = d.instructions
                    }
                    is MealDetailUiState.Error -> {
                        Toast.makeText(this@MealDetailActivity, state.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun observeFavorite() {
        lifecycleScope.launch {
            viewModel.isFavorite.collect { fav ->
                binding.iBtnFavourite.setImageResource(
                    if (fav) android.R.drawable.btn_star_big_on
                    else android.R.drawable.btn_star_big_off
                )
            }
        }
    }
}
