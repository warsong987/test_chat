package ru.ivan.eremin.chat.entity.exception

sealed class AppException(message: String? = null, cause: Throwable? = null) : Exception(message, cause )