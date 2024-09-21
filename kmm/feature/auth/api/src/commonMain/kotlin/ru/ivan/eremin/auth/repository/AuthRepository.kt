package ru.ivan.eremin.auth.repository

interface AuthRepository {
    suspend fun sendAuthCode(phone: String): Boolean
    suspend fun checkAuthCode(phone: String, code: String): Boolean
}