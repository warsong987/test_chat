package ru.ivan.eremin.testchat.domain.authorization.repository

interface AuthorizationRepository {
    suspend fun registration(
        phone: String,
        name: String,
        username: String
    )

    suspend fun sendPhoneForGetCode(phone: String): Boolean

    suspend fun checkAuthCode(phone: String, code: String): Boolean
}