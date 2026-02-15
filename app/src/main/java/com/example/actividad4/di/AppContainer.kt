package com.example.actividad4.di

import android.content.Context
import androidx.room.Room
import com.example.actividad4.data.firebase.FavoritesFirebaseDataSource
import com.example.actividad4.data.local.dao.UserDao
import com.example.actividad4.data.local.db.AppDatabase
import com.example.actividad4.data.remote.RetrofitClient
import com.example.actividad4.data.repository.FavoritesRepository
import com.example.actividad4.data.repository.FavoritesRepositoryImpl
import com.example.actividad4.data.repository.MealsRepository
import com.example.actividad4.data.repository.MealsRepositoryImpl
import com.example.actividad4.data.repository.UserRepository

class AppContainer(context: Context) {

    val database: AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "actividad4_db"
        ).fallbackToDestructiveMigration()
            .build()

    val userDao: UserDao = database.userDao()
    val userRepository = UserRepository(userDao)
    val mealApiService = RetrofitClient.api
    val mealsRepository: MealsRepository = MealsRepositoryImpl(mealApiService)
    val favoriteDao = database.favoriteDao()
    val favoritesFirebaseDataSource = FavoritesFirebaseDataSource()
    val favoritesRepository: FavoritesRepository =
        FavoritesRepositoryImpl(favoriteDao, favoritesFirebaseDataSource)


}
