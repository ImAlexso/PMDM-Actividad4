package com.example.actividad4.ui.favorites

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.actividad4.MyApplication
import com.example.actividad4.databinding.ActivityFavoritesBinding
import com.example.actividad4.domain.model.Meal
import com.example.actividad4.ui.detail.MealDetailActivity
import com.example.actividad4.ui.list.MealsListActivity
import com.example.actividad4.ui.list.adapter.MealsAdapter

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var adapter: MealsAdapter

    private val viewModel: FavoritesViewModel by viewModels {
        val c = (application as MyApplication).appContainer
        FavoritesViewModelFactory(c.favoritesRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getLongExtra(MealsListActivity.EXTRA_USER_ID, -1L)
        if (userId == -1L) {
            finish()
            return
        }

        adapter = MealsAdapter { meal ->
            startActivity(
                Intent(this, MealDetailActivity::class.java)
                    .putExtra(MealsListActivity.EXTRA_USER_ID, userId)
                    .putExtra(MealsListActivity.EXTRA_MEAL_ID, meal.id)
            )
        }

        binding.rvFavorites.layoutManager = LinearLayoutManager(this)
        binding.rvFavorites.adapter = adapter

        // LiveData -> se actualiza solo cuando añades/quitas favoritos
        viewModel.favorites(userId).observe(this) { favs ->
            val meals = favs.map { fav ->
                Meal(
                    id = fav.mealId,
                    name = fav.mealName,
                    imageUrl = fav.mealImageUrl
                )
            }
            adapter.submitList(meals)
        }
    }
}
