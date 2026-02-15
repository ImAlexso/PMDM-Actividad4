package com.example.actividad4.data.repository

import com.example.actividad4.data.local.dao.UserDao
import com.example.actividad4.data.local.entity.UserEntity

class UserRepository (private val userDao: UserDao){
    suspend fun registerUser(user: UserEntity):Long{
        return userDao.insertUser(user)
    }
    suspend fun emailExists(email:String): Boolean{
        return userDao.findUserByEmail(email) != null
    }
    suspend fun login(email: String, password: String): UserEntity? {
        return userDao.login(email, password)
    }

}