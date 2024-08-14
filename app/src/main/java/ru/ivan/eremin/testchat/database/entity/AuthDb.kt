package ru.ivan.eremin.testchat.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AuthDb(
    @PrimaryKey val userId: Long,
    val refreshToken: String,
    val accessToken: String
)