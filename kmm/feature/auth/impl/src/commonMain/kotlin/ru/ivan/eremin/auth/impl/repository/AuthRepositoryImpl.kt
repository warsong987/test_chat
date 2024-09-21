package ru.ivan.eremin.auth.impl.repository

import ru.ivan.eremin.auth.repository.AuthRepository

internal class AuthRepositoryImpl: AuthRepository {
    override suspend fun sendAuthCode(phone: String): Boolean {
        return true
    }

    override suspend fun checkAuthCode(phone: String, code: String): Boolean {
        return false
    }
}