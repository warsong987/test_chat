package ru.ivan.eremin.chat.entity.token

data class AccessTokenData(
    val accessToken: String,
    val refreshToken: String,
    val userName: String
)
