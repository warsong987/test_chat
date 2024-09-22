package ru.ivan.eremin.chat.config

import ru.ivan.eremin.chat.entity.token.AccessTokenData

interface TokenHandler {
    suspend fun getToken(userName: String, oldToken: String? = null): AccessTokenData
}