package com.example.actividad4.ui.list

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.actividad4.MyApplication
import com.example.actividad4.R
import com.example.actividad4.databinding.ActivityMealsListBinding
import com.example.actividad4.ui.detail.MealDetailActivity
import com.example.actividad4.ui.favorites.FavoritesActivity
import com.example.actividad4.ui.list.adapter.MealsAdapter
import kotlinx.coroutines.launch

class MealsListActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER_ID = "extra_user_id"
        const val EXTRA_MEAL_ID = "extra_meal_id"
    }

    private lateinit var binding: ActivityMealsListBinding
    private lateinit var adapter: MealsAdapter

    private var userId: Long = -1L

    private val viewModel: MealsListViewModel by viewModels {
        val c = (application as MyApplication).appContainer
        MealsListViewModelFactory(c.mealsRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔑 Leemos el userId UNA vez
        userId = intent.getLongExtra(EXTRA_USER_ID, -1L)
        if (userId == -1L) {
            finish()
            return
        }

        enableEdgeToEdge()
        binding = ActivityMealsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecycler()
        setupSpinner()
        observeUi()

        // 🔹 Carga inicial: primera categoría (si no es Favoritas)
        val firstUi = resources.getStringArray(R.array.meal_categories).firstOrNull()
        if (firstUi != null && firstUi != "Favoritas") {
            binding.tvCategoryTitle.text = firstUi
            viewModel.loadCategory(mapCategoryToApi(firstUi))
        }
    }

    // -------------------------------
    // RecyclerView
    // -------------------------------
    private fun setupRecycler() {
        adapter = MealsAdapter { meal ->
            startActivity(
                Intent(this, MealDetailActivity::class.java)
                    .putExtra(EXTRA_USER_ID, userId)
                    .putExtra(EXTRA_MEAL_ID, meal.id)
            )
        }

        binding.rvMeals.layoutManager = LinearLayoutManager(this)
        binding.rvMeals.adapter = adapter
    }

    // -------------------------------
    // Spinner
    // -------------------------------
    private fun setupSpinner() {
        val spinnerAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.meal_categories,
            android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        binding.spCategories.adapter = spinnerAdapter

        binding.spCategories.onItemSelectedListener =
            object : android.widget.AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: android.widget.AdapterView<*>?,
                    view: android.view.View?,
                    position: Int,
                    id: Long
                ) {
                    val categoryUi = parent?.getItemAtPosition(position)?.toString() ?: return

                    // ⭐ FAVORITAS → navegar
                    if (categoryUi == "Favoritas") {
                        startActivity(
                            Intent(this@MealsListActivity, FavoritesActivity::class.java)
                                .putExtra(EXTRA_USER_ID, userId)
                        )
                        return
                    }

                    // 🍽️ Categorías normales → API
                    binding.tvCategoryTitle.text = categoryUi
                    viewModel.loadCategory(mapCategoryToApi(categoryUi))
                }

                override fun onNothingSelected(parent: android.widget.AdapterView<*>?) = Unit
            }
    }

    // -------------------------------
    // UI State
    // -------------------------------
    private fun observeUi() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    MealsListUiState.Idle -> Unit
                    MealsListUiState.Loading -> Unit
                    is MealsListUiState.Success -> {
                        adapter.submitList(state.meals)
                    }
                    is MealsListUiState.Error -> {
                        Toast.makeText(
                            this@MealsListActivity,
                            state.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    // -------------------------------
    // Mapeo UI → API
    // -------------------------------
    private fun mapCategoryToApi(categoryUi: String): String {
        return when (categoryUi) {
            "Marisco" -> "Seafood"
            "Pollo" -> "Chicken"
            "Pasta" -> "Pasta"
            "Vegetariana" -> "Vegetarian"
            "Variadas" -> "Miscellaneous"
            else -> categoryUi
        }
    }
}
