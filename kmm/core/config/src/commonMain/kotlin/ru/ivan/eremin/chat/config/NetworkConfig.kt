package ru.ivan.eremin.chat.config

data class NetworkConfig (
    val baseUrl: String,
    val userAgent: String,
    val socketTimeout: Long,
    val connectTimeout: Long
)