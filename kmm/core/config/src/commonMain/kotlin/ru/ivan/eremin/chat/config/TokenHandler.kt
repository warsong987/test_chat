package ru.ivan.eremin.chat.config

import token.AccessTokenData

interface TokenHandler {
    suspend fun getToken(userName: String, oldToken: String? = null): AccessTokenData
}