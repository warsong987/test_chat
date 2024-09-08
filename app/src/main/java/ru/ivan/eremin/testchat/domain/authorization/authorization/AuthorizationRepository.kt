package ru.ivan.eremin.testchat.domain.authorization.authorization

interface AuthorizationRepository {
    suspend fun sendAuthCode(phone: String): Boolean

    suspend fun checkAuthCode(phone: String, code: String): Boolean
}