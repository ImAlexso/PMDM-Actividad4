package com.example.actividad4.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.actividad4.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity): Long
    @Query("SELECT * FROM usuarios WHERE email=:email LIMIT 1")
    suspend fun findUserByEmail(email: String): UserEntity?
    @Query("SELECT * FROM usuarios WHERE id=:userId LIMIT 1")
    suspend fun findUserById(userId: Int): UserEntity?
    @Query("SELECT * FROM usuarios")
    fun getUsers(): Flow<List<UserEntity>>
    @Query(" SELECT * FROM usuarios WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): UserEntity?

}