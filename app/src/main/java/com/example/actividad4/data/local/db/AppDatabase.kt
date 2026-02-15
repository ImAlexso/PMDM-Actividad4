package com.example.actividad4.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.actividad4.data.local.dao.FavoriteDao
import com.example.actividad4.data.local.dao.UserDao
import com.example.actividad4.data.local.entity.FavoriteEntity
import com.example.actividad4.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, FavoriteEntity::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun favoriteDao(): FavoriteDao
}
