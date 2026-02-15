package com.example.actividad4.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.actividad4.R
import com.example.actividad4.domain.model.Meal

class MealsAdapter(
    private val onClick: (Meal) -> Unit
) : RecyclerView.Adapter<MealViewHolder>() {

    private val items = mutableListOf<Meal>()

    fun submitList(newItems: List<Meal>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(items[position], onClick)
    }

    override fun getItemCount(): Int = items.size
}
