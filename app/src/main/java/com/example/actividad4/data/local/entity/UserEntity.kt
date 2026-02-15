package com.example.actividad4.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "usuarios",
    indices = [Index(value = ["email"], unique = true)],
)

data class UserEntity (
    @PrimaryKey (autoGenerate = true)
    val id:Int =0,
    val email: String,
    val password:String
)
