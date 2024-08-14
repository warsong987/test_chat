package ru.ivan.eremin.testchat.domain.authorization.repository

interface AuthorizationRepository {
    suspend fun registration(
        phone: String,
        name: String,
        username: String
    )

    suspend fun getCurrentPhoneNumber(): String
}