package com.example.actividad4.ui.list.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.actividad4.R
import com.example.actividad4.domain.model.Meal

class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvMealName: TextView = itemView.findViewById(R.id.tvMealName)
    private val ivMealImage: ImageView = itemView.findViewById(R.id.ivMealImage)

    fun bind(meal: Meal, onClick: (Meal) -> Unit) {
        tvMealName.text = meal.name

        ivMealImage.load(meal.imageUrl) {
            crossfade(true)
        }

        itemView.setOnClickListener {
            onClick(meal)
        }
    }
}

